package com.amazonaws.emr.node.provisioner;

import com.amazonaws.emr.node.provisioner.bigtop.Deployer;
import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.config.ConfigFactory;
import com.amazonaws.emr.node.provisioner.puppet.api.PuppetException;
import java.io.IOException;

public final class NodeProvisioner {
   private final ConfigApplier applier;
   private final Deployer deployer;

   public NodeProvisioner(ConfigApplier applier, Deployer deployer) {
      this.applier = applier;
      this.deployer = deployer;
   }

   public void provision() throws IOException, ConfigException, PuppetException {
      Config config = this.loadDefaultConfig();
      this.applier.applyTo(config);
      this.deployer.deploy(config);
   }

   private Config loadDefaultConfig() {
      return ConfigFactory.createFromYamlResource("/default-config.yaml");
   }
}
