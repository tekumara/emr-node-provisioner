package com.amazonaws.emr.node.provisioner.puppet.api;

import com.amazonaws.emr.node.provisioner.exec.Command;
import com.amazonaws.emr.node.provisioner.exec.CommandExecutor;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

public final class ApplyCommand implements Callable<ApplyStatus> {
   private boolean debug = false;
   private boolean verbose = false;
   private boolean detailedExitcodes = false;
   private String manifestsDirectory;
   private CommandExecutor executor;

   ApplyCommand(CommandExecutor executor) {
      this.executor = executor;
   }

   public ApplyCommand setDebug(boolean debug) {
      this.debug = debug;
      return this;
   }

   public ApplyCommand setVerbose(boolean verbose) {
      this.verbose = verbose;
      return this;
   }

   public ApplyCommand setDetailedExitcodes(boolean detailedExitcodes) {
      this.detailedExitcodes = detailedExitcodes;
      return this;
   }

   public ApplyCommand setManifestsDirectory(String manifestsDirectory) {
      this.manifestsDirectory = manifestsDirectory;
      return this;
   }

   public ApplyCommand setManifestsDirectory(File manifestsDirectory) {
      return this.setManifestsDirectory(manifestsDirectory.getAbsolutePath());
   }

   public ApplyStatus call() throws PuppetException, IOException {
      this.validateCall();
      Command command = (new Command.Builder("apply")).setFlag("debug", this.debug).setFlag("verbose", this.verbose).setFlag("detailed-exitcodes", this.detailedExitcodes).addArguments(this.manifestsDirectory).build();
      return this.handleExitcode(this.executor.execute(command));
   }

   private void validateCall() {
      if (this.manifestsDirectory == null) {
         throw new IllegalArgumentException("Puppet apply requires a manifest file.");
      }
   }

   private ApplyStatus handleExitcode(int exitcode) throws PuppetException {
      switch(exitcode) {
      case 0:
         return ApplyStatus.NO_CHANGES;
      case 1:
      case 3:
      case 5:
      default:
         throw new PuppetException("Unknown puppet apply exit code: " + exitcode);
      case 2:
         return ApplyStatus.APPLIED_CHANGES;
      case 4:
         throw new PuppetException("Unable to complete transaction and no changes were applied.");
      case 6:
         throw new PuppetException("Unable to complete transaction and some changes were applied.");
      }
   }
}
