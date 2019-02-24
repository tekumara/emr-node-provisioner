package com.amazonaws.emr.node.provisioner.bigtop.deploy.provider;

import com.amazonaws.emr.node.provisioner.bigtop.Deployer;
import com.amazonaws.emr.node.provisioner.util.preconditions.CommonPreconditions;

public final class StaticDeployerProvider implements DeployerProvider {
   private final Deployer deployer;

   private StaticDeployerProvider(Deployer deployer) {
      this.deployer = deployer;
   }

   public static StaticDeployerProvider providing(Deployer deployer) {
      CommonPreconditions.require(deployer, "deployer");
      return new StaticDeployerProvider(deployer);
   }

   public Deployer provide() {
      return this.deployer;
   }
}
