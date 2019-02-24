package com.amazonaws.emr.node.provisioner.bigtop;

import com.amazonaws.emr.node.provisioner.bigtop.hiera.HieraConfigApplier;
import com.amazonaws.emr.node.provisioner.bigtop.hiera.data.DataSource;
import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.config.ConfigFactory;
import com.amazonaws.emr.node.provisioner.json.JsonKeyValueMaskerFactory;
import com.amazonaws.emr.node.provisioner.puppet.hiera.HieraFileLayout;
import com.amazonaws.emr.node.provisioner.puppet.hiera.HieraInstaller;
import com.amazonaws.emr.node.provisioner.util.StringUtil;
import com.amazonaws.emr.node.provisioner.util.YamlUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PuppetConfigurator implements Configurator {
   private static final Logger logger = LoggerFactory.getLogger(PuppetConfigurator.class);
   private static final YamlUtil.ScalarStyle DATA_SOURCE_SCALAR_STYLE;
   private static final YamlUtil.ScalarStyle CONFIG_FILE_SCALAR_STYLE;
   private final HieraInstaller hieraInstaller;
   private final HieraFileLayout hieraFileLayout;
   private final List<DataSource> generatedDataSources;

   private PuppetConfigurator(PuppetConfigurator.Builder builder) {
      this.hieraInstaller = builder.hieraInstaller;
      this.hieraFileLayout = builder.hieraFileLayout;
      this.generatedDataSources = builder.generatedDataSources;
   }

   public static PuppetConfigurator.Builder builder() {
      return new PuppetConfigurator.Builder();
   }

   public void configure(Config config) throws IOException {
      this.installHieraFiles();
      this.writeSiteDataSource(config);
   }

   public void cleanUp() throws IOException {
      this.hieraInstaller.deleteDataSourceFile(BigtopDataSource.SITE.getName());
   }

   private void installHieraFiles() throws IOException {
      this.installConfigFile();
      this.hieraInstaller.installDataDirectory(this.hieraFileLayout.getDataDirectory());
      this.writeGeneratedDataSourcesOnce();
   }

   private void installConfigFile() throws IOException {
      Config config = this.readSourceConfigFile();
      List dataSourceNames = collectDataSourceNames(this.generatedDataSources);

      try {
         HieraConfigApplier.forAddingDataSourceNames(dataSourceNames).applyTo(config);
      } catch (ConfigException var4) {
         throw new RuntimeException("Failed adding data source names: " + dataSourceNames, var4);
      }

      this.writeDestinationConfigFile(config);
   }

   private Config readSourceConfigFile() throws IOException {
      String yaml = FileUtils.readFileToString(this.hieraFileLayout.getConfigFile());
      return ConfigFactory.createFromYaml(yaml);
   }

   private void writeDestinationConfigFile(Config config) throws IOException {
      String serialized = YamlUtil.dump(config, CONFIG_FILE_SCALAR_STYLE);
      this.hieraInstaller.writeConfigFile(serialized);
      this.logConfig(config, this.hieraFileLayout.getConfigFile().getName(), CONFIG_FILE_SCALAR_STYLE);
   }

   private void writeSiteDataSource(Config config) throws IOException {
      String serialized = YamlUtil.dump(config, DATA_SOURCE_SCALAR_STYLE);
      this.hieraInstaller.writeDataSourceFile(serialized, BigtopDataSource.SITE.getName());
      this.logConfig(config, BigtopDataSource.SITE.getName(), DATA_SOURCE_SCALAR_STYLE);
   }

   private void writeGeneratedDataSourcesOnce() throws IOException {
      Iterator var1 = this.generatedDataSources.iterator();

      while(var1.hasNext()) {
         DataSource dataSource = (DataSource)var1.next();
         String fileName = dataSource.getFileName();
         if (!this.hieraInstaller.hasDataSourceFile(fileName)) {
            this.hieraInstaller.writeDataSourceFile(dataSource.getContent(), fileName);
         }
      }

   }

   private void logConfig(Config config, String configName, YamlUtil.ScalarStyle scalarStyle) throws IOException {
      Object maskedConfig = JsonKeyValueMaskerFactory.createDefault().mask(config.unwrapped(), Object.class);
      String serialized = YamlUtil.dump(maskedConfig, scalarStyle);
      String indentedContents = StringUtil.indent(serialized);
      logger.info("contents of {}:\n{}", configName, indentedContents);
   }

   private static List<String> collectDataSourceNames(List<DataSource> dataSources) {
      List<String> dataSourceNames = Lists.newArrayList();
      Iterator var2 = dataSources.iterator();

      while(var2.hasNext()) {
         DataSource generatedDataSource = (DataSource)var2.next();
         dataSourceNames.add(generatedDataSource.getName());
      }

      return dataSourceNames;
   }

   static {
      DATA_SOURCE_SCALAR_STYLE = YamlUtil.ScalarStyle.SINGLE_QUOTED;
      CONFIG_FILE_SCALAR_STYLE = YamlUtil.ScalarStyle.PLAIN;
   }

   public static final class Builder {
      private HieraInstaller hieraInstaller;
      private HieraFileLayout hieraFileLayout;
      private List<DataSource> generatedDataSources;

      private Builder() {
         this.hieraInstaller = new HieraInstaller();
         this.generatedDataSources = Lists.newArrayList();
      }

      public PuppetConfigurator.Builder withHieraInstaller(HieraInstaller hieraInstaller) {
         this.hieraInstaller = hieraInstaller;
         return this;
      }

      public PuppetConfigurator.Builder withPuppetDirectory(File puppetDirectory) {
         this.hieraFileLayout = new HieraFileLayout(puppetDirectory);
         return this;
      }

      public PuppetConfigurator.Builder withGeneratedDataSources(DataSource... dataSources) {
         this.generatedDataSources.addAll(Arrays.asList(dataSources));
         return this;
      }

      public PuppetConfigurator build() {
         Preconditions.checkNotNull(this.hieraFileLayout, "HieraFileLayout is required");
         List<String> sourceFileNames = collectDataSourceFileNames(this.generatedDataSources);
         checkDoesNotContainSiteName(sourceFileNames);
         checkAllUniqueFileNames(sourceFileNames);
         return new PuppetConfigurator(this);
      }

      private static void checkDoesNotContainSiteName(List<String> sourceFileNames) {
         String restrictedName = BigtopDataSource.SITE.getName();
         Preconditions.checkArgument(!sourceFileNames.contains(restrictedName), "GeneratedDataSources must not contain source named {}", new Object[]{restrictedName});
      }

      private static void checkAllUniqueFileNames(List<String> sourceFileNames) {
         HashSet<String> setOfNames = Sets.newHashSet(sourceFileNames);
         Preconditions.checkArgument(sourceFileNames.size() == setOfNames.size(), "GeneratedDataSources must not contain sources with duplicate names {}", new Object[]{sourceFileNames});
      }

      private static List<String> collectDataSourceFileNames(List<DataSource> dataSources) {
         List<String> dataSourceFileNames = Lists.newArrayList();
         Iterator var2 = dataSources.iterator();

         while(var2.hasNext()) {
            DataSource generatedDataSource = (DataSource)var2.next();
            dataSourceFileNames.add(generatedDataSource.getFileName());
         }

         return dataSourceFileNames;
      }
   }
}
