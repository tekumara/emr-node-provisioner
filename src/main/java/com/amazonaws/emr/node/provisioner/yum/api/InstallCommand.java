package com.amazonaws.emr.node.provisioner.yum.api;

import com.amazonaws.emr.node.provisioner.exec.Command;
import com.amazonaws.emr.node.provisioner.exec.CommandExecutor;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public final class InstallCommand implements Callable<Void> {
   private final CommandExecutor executor;
   private final List<String> packages;

   public InstallCommand(CommandExecutor executor) {
      this.executor = executor;
      this.packages = Lists.newArrayList();
   }

   public InstallCommand withPackage(String packageName) {
      this.packages.add(packageName);
      return this;
   }

   public InstallCommand withPackages(Collection<String> packageNames) {
      this.packages.addAll(packageNames);
      return this;
   }

   public Void call() throws IOException, YumException {
      this.validateCall();
      Command command = (new Command.Builder("install")).addArguments(this.packages).build();
      this.handleExitcode(this.executor.execute(command));
      return null;
   }

   private void validateCall() {
      Preconditions.checkArgument(!this.packages.isEmpty(), "No packages to install");
   }

   private void handleExitcode(int exitcode) throws YumException {
      switch(exitcode) {
      case 0:
         return;
      default:
         throw new YumException("Yum install failed with exitcode: " + exitcode);
      }
   }
}
