package com.amazonaws.emr.node.provisioner.platform.model;

import com.google.api.client.util.Key;

public final class Property {
   @Key
   private String key;
   @Key
   private String value;

   public Property() {
   }

   public String getKey() {
      return this.key;
   }

   public String getValue() {
      return this.value;
   }
}
