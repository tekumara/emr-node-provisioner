package com.amazonaws.emr.node.provisioner.bigtop;

import com.amazonaws.emr.node.provisioner.puppet.PuppetFactory;
import com.amazonaws.emr.node.provisioner.puppet.api.ApplyCommand;
import com.amazonaws.emr.node.provisioner.puppet.api.ApplyStatus;
import com.amazonaws.emr.node.provisioner.puppet.api.Puppet;
import com.amazonaws.emr.node.provisioner.puppet.api.PuppetColor;
import com.amazonaws.emr.node.provisioner.puppet.api.PuppetException;
import com.amazonaws.emr.node.provisioner.puppet.api.PuppetOptions;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BigtopPuppeteer implements Puppeteer {
   private static final Logger logger = LoggerFactory.getLogger(BigtopPuppeteer.class);
   private static final String MODULES_DIRECTORY = "modules";
   private static final String MANIFESTS_DIRECTORY = "manifests";
   private static final String EXTERNAL_MODULES_DIRECTORY = "/etc/puppet/modules";
   private final PuppetFactory puppetFactory;
   private final File modulesDirectory;
   private final File manifestsDirectory;

   public BigtopPuppeteer(PuppetFactory puppetFactory, File puppetDirectory) {
      this.puppetFactory = puppetFactory;
      this.modulesDirectory = new File(puppetDirectory, "modules");
      this.manifestsDirectory = new File(puppetDirectory, "manifests");
   }

   public void applyPuppet() throws PuppetException {
      PuppetOptions options = (new PuppetOptions()).addModulePath(this.modulesDirectory).addModulePath("/etc/puppet/modules").setUsingFutureParser(true).setColor(PuppetColor.FALSE).setEvaltrace(true);
      Puppet puppet = this.puppetFactory.create(options);
      ApplyCommand command = puppet.apply().setManifestsDirectory(this.manifestsDirectory).setDebug(true).setDetailedExitcodes(true).setVerbose(true);

      try {
         ApplyStatus status = command.call();
         logger.info("Applied puppet: " + status.getDescription());
      } catch (IOException var5) {
         throw new RuntimeException("Failed to call puppet apply.", var5);
      }
   }
}
