package com.amazonaws.emr.node.provisioner.bigtop.deploy.provider;

import com.amazonaws.emr.node.provisioner.bigtop.BigtopDeployer;
import com.amazonaws.emr.node.provisioner.bigtop.BigtopPuppeteer;
import com.amazonaws.emr.node.provisioner.bigtop.Configurator;
import com.amazonaws.emr.node.provisioner.bigtop.Deployer;
import com.amazonaws.emr.node.provisioner.bigtop.PuppetConfigurator;
import com.amazonaws.emr.node.provisioner.bigtop.Puppeteer;
import com.amazonaws.emr.node.provisioner.bigtop.hiera.data.DataSource;
import com.amazonaws.emr.node.provisioner.bigtop.hiera.data.generator.DataSourceGenerator;
import com.amazonaws.emr.node.provisioner.bigtop.hiera.data.generator.DataSourceGenerators;
import com.amazonaws.emr.node.provisioner.puppet.PuppetFactory;
import com.amazonaws.emr.node.provisioner.puppet.hiera.HieraInstaller;
import com.amazonaws.emr.node.provisioner.util.preconditions.CommonPreconditions;
import com.amazonaws.emr.node.provisioner.util.random.RandomProvider;
import com.amazonaws.emr.node.provisioner.util.random.SecureRandomProvider;
import java.io.File;
import java.util.Random;

public final class BigtopDeployerProvider implements DeployerProvider {
   private static final String DEFAULT_BIGTOP_PUPPET_DIRECTORY = "/var/aws/emr/bigtop-deploy/puppet";
   private static final String DEFAULT_PUPPET_CONF_DIRECTORY = "/etc/puppet";
   private static final String PASSWORD_DATA_SOURCE_NAME = "generated";
   private final File bigtopPuppetDirectory;
   private final File puppetConfDirectory;
   private final PuppetFactory puppetFactory;
   private final RandomProvider randomProvider;

   private BigtopDeployerProvider(BigtopDeployerProvider.Builder builder) {
      this.bigtopPuppetDirectory = builder.bigtopPuppetDirectory;
      this.puppetConfDirectory = builder.puppetConfDirectory;
      this.randomProvider = builder.randomProvider;
      this.puppetFactory = builder.puppetFactory;
   }

   public static BigtopDeployerProvider defaultProvider() {
      return builder().build();
   }

   public static BigtopDeployerProvider.Builder builder() {
      return new BigtopDeployerProvider.Builder();
   }

   public Deployer provide() {
      DataSource passwordDataSource = this.generatePasswordDataSource();
      Configurator configurator = PuppetConfigurator.builder().withHieraInstaller(HieraInstaller.installingTo(this.puppetConfDirectory)).withPuppetDirectory(this.bigtopPuppetDirectory).withGeneratedDataSources(passwordDataSource).build();
      Puppeteer puppeteer = new BigtopPuppeteer(this.puppetFactory, this.bigtopPuppetDirectory);
      return new BigtopDeployer(configurator, puppeteer);
   }

   private DataSource generatePasswordDataSource() {
      Random random = this.randomProvider.provide();
      DataSourceGenerator generator = DataSourceGenerators.passwordGenerator("generated", random);
      return generator.generate();
   }

   public static final class Builder {
      private File bigtopPuppetDirectory;
      private File puppetConfDirectory;
      private PuppetFactory puppetFactory;
      private RandomProvider randomProvider;

      private Builder() {
         this.bigtopPuppetDirectory = new File("/var/aws/emr/bigtop-deploy/puppet");
         this.puppetConfDirectory = new File("/etc/puppet");
         this.puppetFactory = new PuppetFactory();
         this.randomProvider = SecureRandomProvider.defaultProvider();
      }

      public BigtopDeployerProvider.Builder withBigtopPuppetDirectory(File puppetBigtopDirectory) {
         this.bigtopPuppetDirectory = puppetBigtopDirectory;
         return this;
      }

      public BigtopDeployerProvider.Builder withPuppetConfDirectory(File puppetConfDirectory) {
         this.puppetConfDirectory = puppetConfDirectory;
         return this;
      }

      public BigtopDeployerProvider.Builder withPuppetFactory(PuppetFactory puppetFactory) {
         this.puppetFactory = puppetFactory;
         return this;
      }

      public BigtopDeployerProvider.Builder withRandomProvider(RandomProvider randomProvider) {
         this.randomProvider = randomProvider;
         return this;
      }

      public BigtopDeployerProvider build() {
         CommonPreconditions.require(this.bigtopPuppetDirectory, "bigtopPuppetDirectory");
         CommonPreconditions.require(this.puppetConfDirectory, "puppetConfDirectory");
         CommonPreconditions.require(this.puppetFactory, "puppetFactory");
         CommonPreconditions.require(this.randomProvider, "randomProvider");
         return new BigtopDeployerProvider(this);
      }
   }
}
