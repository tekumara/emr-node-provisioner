package com.amazonaws.emr.node.provisioner.platform.model;

public final class DoProvisionCheckinRequest {
   private final ProvisionStatus provisionStatus;
   private ProvisionFailureReason provisionFailureReason;
   private String message;

   public DoProvisionCheckinRequest(ProvisionStatus provisionStatus) {
      this.provisionStatus = provisionStatus;
   }

   public DoProvisionCheckinRequest withProvisionFailureReason(ProvisionFailureReason provisionFailureReason) {
      this.provisionFailureReason = provisionFailureReason;
      return this;
   }

   public DoProvisionCheckinRequest withMessage(String message) {
      this.message = message;
      return this;
   }

   public ProvisionStatus getProvisionStatus() {
      return this.provisionStatus;
   }

   public ProvisionFailureReason getProvisionFailureReason() {
      return this.provisionFailureReason;
   }

   public String getMessage() {
      return this.message;
   }
}
