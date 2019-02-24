package com.amazonaws.emr.node.provisioner.puppet.api;

public enum ApplyStatus {
   NO_CHANGES("There were no changes to apply."),
   APPLIED_CHANGES("Changes were successfully applied.");

   private final String description;

   private ApplyStatus(String description) {
      this.description = description;
   }

   public String getDescription() {
      return this.description;
   }
}
