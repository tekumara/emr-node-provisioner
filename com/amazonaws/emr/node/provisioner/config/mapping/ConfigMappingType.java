package com.amazonaws.emr.node.provisioner.config.mapping;

import com.google.common.base.Joiner;

public enum ConfigMappingType {
   CONFIG_OVERRIDE("config-override"),
   COMMA_DELIMITED_LIST("comma-delimited-list"),
   STRING("string"),
   BOOLEAN("boolean"),
   DNS_REVERSE_LOOKUP("dns-reverse-lookup"),
   STATIC_BASE_PATH("static-base-path");

   private final String name;

   private ConfigMappingType(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public static ConfigMappingType fromName(String name) {
      ConfigMappingType[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ConfigMappingType type = var1[var3];
         if (name.equalsIgnoreCase(type.name)) {
            return type;
         }
      }

      throw new IllegalArgumentException("Invalid name " + name + ". must be one of " + Joiner.on(", ").join(values()));
   }

   public String toString() {
      return this.name;
   }
}
