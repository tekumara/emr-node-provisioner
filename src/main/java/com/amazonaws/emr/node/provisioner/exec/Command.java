package com.amazonaws.emr.node.provisioner.exec;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public final class Command {
   private String name;
   private Map<String, String> options;
   private Set<String> flags;
   private List<String> arguments;

   public Command(String name) {
      this.name = name;
      this.options = ImmutableMap.of();
      this.flags = ImmutableSet.of();
      this.arguments = ImmutableList.of();
   }

   private Command(Command.Builder builder) {
      this.name = builder.name;
      this.options = ImmutableMap.copyOf(builder.options);
      this.flags = ImmutableSet.copyOf(builder.flags);
      this.arguments = ImmutableList.copyOf(builder.arguments);
   }

   public String getName() {
      return this.name;
   }

   public Map<String, String> getOptions() {
      return this.options;
   }

   public Set<String> getFlags() {
      return this.flags;
   }

   public List<String> getArguments() {
      return this.arguments;
   }

   public static final class Builder {
      private String name;
      private Map<String, String> options = new TreeMap();
      private Set<String> flags = new TreeSet();
      private List<String> arguments = new ArrayList();

      public Builder(String name) {
         this.name = name;
      }

      public Command.Builder setOption(String option, String value) {
         this.options.put(option, value);
         return this;
      }

      public Command.Builder setFlag(String flag, boolean value) {
         if (value) {
            this.flags.add(flag);
         } else {
            this.flags.remove(flag);
         }

         return this;
      }

      public Command.Builder addArguments(List<String> arguments) {
         this.arguments.addAll(arguments);
         return this;
      }

      public Command.Builder addArguments(String... arguments) {
         this.addArguments(Arrays.asList(arguments));
         return this;
      }

      public Command build() {
         return new Command(this);
      }
   }
}
