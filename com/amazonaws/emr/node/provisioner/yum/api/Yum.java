package com.amazonaws.emr.node.provisioner.yum.api;

import com.amazonaws.emr.node.provisioner.exec.Command;
import com.amazonaws.emr.node.provisioner.exec.CommandExecutor;

public final class Yum {
   private final CommandExecutor executor;

   public Yum(String executable) {
      this(executable, new YumOptions());
   }

   public Yum(String executable, YumOptions options) {
      Command base = this.buildBaseCommand(executable, options);
      this.executor = new CommandExecutor(base);
   }

   public InstallCommand install() {
      return new InstallCommand(this.executor);
   }

   private Command buildBaseCommand(String executable, YumOptions options) {
      Command.Builder builder = (new Command.Builder(executable)).setFlag("y", options.shouldAssumeYes());
      if (options.hasDebugLevel()) {
         builder.setOption("d", options.getDebugLevel().toString());
      }

      if (options.hasErrorLevel()) {
         builder.setOption("e", options.getErrorLevel().toString());
      }

      return builder.build();
   }
}
