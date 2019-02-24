package com.amazonaws.emr.node.provisioner.exec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.exec.CommandLine;

public final class CommandLineBuilder {
   private final String executable;
   private final List<String> arguments;

   public CommandLineBuilder(String executable) {
      this.executable = executable;
      this.arguments = new ArrayList();
   }

   public CommandLineBuilder addFlag(String flag) {
      this.addArgument(this.getPrefix(flag) + flag);
      return this;
   }

   public CommandLineBuilder addFlags(Collection<String> flags) {
      Iterator var2 = flags.iterator();

      while(var2.hasNext()) {
         String flag = (String)var2.next();
         this.addFlag(flag);
      }

      return this;
   }

   public CommandLineBuilder addOption(String option, String value) {
      this.addArgument(this.getPrefix(option) + option);
      this.addArgument(value);
      return this;
   }

   public CommandLineBuilder addOptions(Map<String, String> options) {
      Iterator var2 = options.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<String, String> entry = (Entry)var2.next();
         this.addOption((String)entry.getKey(), (String)entry.getValue());
      }

      return this;
   }

   public CommandLineBuilder addArgument(String argument) {
      this.arguments.add(argument);
      return this;
   }

   public CommandLineBuilder addArguments(Collection<String> arguments) {
      Iterator var2 = arguments.iterator();

      while(var2.hasNext()) {
         String argument = (String)var2.next();
         this.addArgument(argument);
      }

      return this;
   }

   public CommandLine build() {
      String[] args = (String[])this.arguments.toArray(new String[this.arguments.size()]);
      return (new CommandLine(this.executable)).addArguments(args, false);
   }

   private String getPrefix(String name) {
      return name.length() == 1 ? "-" : "--";
   }
}
