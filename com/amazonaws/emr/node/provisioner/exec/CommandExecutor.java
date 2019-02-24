package com.amazonaws.emr.node.provisioner.exec;

import java.io.IOException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;

public final class CommandExecutor {
   private final Command baseCommand;

   public CommandExecutor(Command baseCommand) {
      this.baseCommand = baseCommand;
   }

   public int execute(Command subCommand) throws IOException {
      CommandLine line = this.buildCommandLine(subCommand);
      Executor executor = new DefaultExecutor();
      executor.setExitValues((int[])null);
      return executor.execute(line);
   }

   private CommandLine buildCommandLine(Command subCommand) {
      return this.createBaseCommandLineBuilder(subCommand.getName()).addFlags(subCommand.getFlags()).addOptions(subCommand.getOptions()).addArguments(subCommand.getArguments()).build();
   }

   private CommandLineBuilder createBaseCommandLineBuilder(String subCommand) {
      return (new CommandLineBuilder(this.baseCommand.getName())).addArgument(subCommand).addFlags(this.baseCommand.getFlags()).addOptions(this.baseCommand.getOptions()).addArguments(this.baseCommand.getArguments());
   }
}
