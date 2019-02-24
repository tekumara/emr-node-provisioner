package com.amazonaws.emr.node.provisioner.config.hue.behavior;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Joiner;

@JsonDeserialize(
   using = HueAppVisibilityDeserializer.class
)
public enum HueAppVisibility {
   REMOVED("removed"),
   VISIBLE("visible");

   private final String name;

   private HueAppVisibility(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      return this.name;
   }

   public static HueAppVisibility fromName(String name) {
      HueAppVisibility[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         HueAppVisibility type = var1[var3];
         if (name.equalsIgnoreCase(type.name)) {
            return type;
         }
      }

      throw new IllegalArgumentException("Invalid name " + name + ". must be one of " + Joiner.on(", ").join(values()));
   }

   public static HueAppVisibility maximum(HueAppVisibility firstVisibility, HueAppVisibility secondVisibility) {
      return firstVisibility.equals(VISIBLE) ? VISIBLE : secondVisibility;
   }
}
