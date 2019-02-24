package com.amazonaws.emr.node.provisioner;

import com.amazonaws.emr.node.provisioner.bigtop.Deployer;
import com.amazonaws.emr.node.provisioner.bigtop.config.PlatformConfigApplierFactory;
import com.amazonaws.emr.node.provisioner.bigtop.config.PlatformContext;
import com.amazonaws.emr.node.provisioner.bigtop.config.RepoConfigApplier;
import com.amazonaws.emr.node.provisioner.bigtop.deploy.provider.BigtopDeployerProvider;
import com.amazonaws.emr.node.provisioner.bigtop.deploy.provider.DeployerProvider;
import com.amazonaws.emr.node.provisioner.config.CompositeConfigApplier;
import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import com.amazonaws.emr.node.provisioner.util.preconditions.CommonPreconditions;
import java.io.IOException;

public final class NodeProvisionerFactory {
   private final DeployerProvider deployerProvider;
   private final PlatformConfigApplierFactory applierFactory;

   private NodeProvisionerFactory(NodeProvisionerFactory.Builder builder) {
      this.deployerProvider = builder.deployerProvider;
      this.applierFactory = builder.applierFactory;
   }

   public static NodeProvisionerFactory defaultFactory() {
      return builder().build();
   }

   public static NodeProvisionerFactory.Builder builder() {
      return new NodeProvisionerFactory.Builder();
   }

   public NodeProvisioner create(String repoUri, PlatformContext platformContext, boolean provisionRepo) throws IOException {
      ConfigApplier configApplier = CompositeConfigApplier.builder().addAppliers(new RepoConfigApplier(repoUri, provisionRepo)).addAppliers(this.applierFactory.create(platformContext)).build();
      Deployer deployer = this.deployerProvider.provide();
      return new NodeProvisioner(configApplier, deployer);
   }

   public static final class Builder {
      private PlatformConfigApplierFactory applierFactory = PlatformConfigApplierFactory.defaultFactory();
      private DeployerProvider deployerProvider = BigtopDeployerProvider.defaultProvider();

      public Builder() {
      }

      public NodeProvisionerFactory.Builder withApplierFactory(PlatformConfigApplierFactory applierFactory) {
         this.applierFactory = applierFactory;
         return this;
      }

      public NodeProvisionerFactory.Builder withDeployerProvider(DeployerProvider deployerProvider) {
         this.deployerProvider = deployerProvider;
         return this;
      }

      public NodeProvisionerFactory build() {
         CommonPreconditions.require(this.applierFactory, "applierFactory");
         CommonPreconditions.require(this.deployerProvider, "deployerProvider");
         return new NodeProvisionerFactory(this);
      }
   }
}
