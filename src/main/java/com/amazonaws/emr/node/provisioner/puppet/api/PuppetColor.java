package com.amazonaws.emr.node.provisioner.puppet.api;

public enum PuppetColor {
   ANSI("ansi"),
   HTML("html"),
   TRUE("true"),
   FALSE("false");

   private final String value;

   private PuppetColor(String value) {
      this.value = value;
   }

   public String getValue() {
      return this.value;
   }
}
