package com.amazonaws.emr.node.provisioner.bigtop.hiera.data.generator;

import com.amazonaws.emr.node.provisioner.bigtop.hiera.data.DataSource;
import com.amazonaws.emr.node.provisioner.bigtop.hiera.data.DataSources;
import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.util.preconditions.CommonPreconditions;

public final class ConfigDataSourceGenerator implements DataSourceGenerator {
   private final String dataSourceName;
   private final ConfigApplier configApplier;

   private ConfigDataSourceGenerator(String dataSourceName, ConfigApplier configApplier) {
      this.dataSourceName = dataSourceName;
      this.configApplier = configApplier;
   }

   public static ConfigDataSourceGenerator newInstance(String dataSourceName, ConfigApplier configApplier) {
      CommonPreconditions.require(dataSourceName, "dataSourceName");
      CommonPreconditions.require(configApplier, "configApplier");
      return new ConfigDataSourceGenerator(dataSourceName, configApplier);
   }

   public DataSource generate() {
      Config config = Config.empty();

      try {
         this.configApplier.applyTo(config);
      } catch (ConfigException var3) {
         throw new RuntimeException(var3);
      }

      return DataSources.yamlBacked(this.dataSourceName, config);
   }
}
