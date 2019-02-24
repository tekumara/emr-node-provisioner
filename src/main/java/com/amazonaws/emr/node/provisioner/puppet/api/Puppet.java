package com.amazonaws.emr.node.provisioner.puppet.api;

import com.amazonaws.emr.node.provisioner.exec.Command;
import com.amazonaws.emr.node.provisioner.exec.CommandExecutor;
import com.google.common.base.Joiner;

public final class Puppet {
   private final CommandExecutor executor;

   public Puppet(String executable) {
      this(executable, new PuppetOptions());
   }

   public Puppet(String executable, PuppetOptions options) {
      Command base = this.createBaseCommand(executable, options);
      this.executor = new CommandExecutor(base);
   }

   public ApplyCommand apply() {
      return new ApplyCommand(this.executor);
   }

   private Command createBaseCommand(String executable, PuppetOptions options) {
      Command.Builder builder = (new Command.Builder(executable)).setFlag("evaltrace", options.isEvaltrace()).setOption("color", options.getColor().getValue()).setOption("modulepath", Joiner.on(":").join(options.getModulePaths()));
      if (options.isUsingFutureParser()) {
         builder.setOption("parser", "future");
      }

      return builder.build();
   }
}
