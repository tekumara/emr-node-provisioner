package com.amazonaws.emr.node.provisioner.bigtop;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.puppet.api.PuppetException;
import java.io.IOException;

public final class BigtopDeployer implements Deployer {
   private final Configurator configurator;
   private final Puppeteer puppeteer;

   public BigtopDeployer(Configurator configurator, Puppeteer puppeteer) {
      this.configurator = configurator;
      this.puppeteer = puppeteer;
   }

   public void deploy(Config config) throws IOException, PuppetException {
      this.configurator.configure(config);
      this.puppeteer.applyPuppet();
      this.configurator.cleanUp();
   }
}
