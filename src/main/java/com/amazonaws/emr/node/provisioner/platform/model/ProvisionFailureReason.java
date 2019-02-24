package com.amazonaws.emr.node.provisioner.platform.model;

public enum ProvisionFailureReason {
   NONE,
   INTERNAL_ERROR,
   CONFIG_ERROR,
   DEPLOYMENT_ERROR,
   UNKNOWN_ERROR;

   private ProvisionFailureReason() {
   }
}
