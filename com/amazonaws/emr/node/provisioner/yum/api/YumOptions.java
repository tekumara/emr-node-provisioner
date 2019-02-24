package com.amazonaws.emr.node.provisioner.yum.api;

public final class YumOptions {
   private boolean assumeYes = false;
   private Integer debugLevel;
   private Integer errorLevel;

   public YumOptions() {
   }

   public boolean shouldAssumeYes() {
      return this.assumeYes;
   }

   public YumOptions withAssumeYes(boolean assumeYes) {
      this.assumeYes = assumeYes;
      return this;
   }

   public Integer getDebugLevel() {
      return this.debugLevel;
   }

   public boolean hasDebugLevel() {
      return this.debugLevel != null;
   }

   public YumOptions withDebugLevel(Integer debugLevel) {
      this.debugLevel = debugLevel;
      return this;
   }

   public boolean hasErrorLevel() {
      return this.errorLevel != null;
   }

   public Integer getErrorLevel() {
      return this.errorLevel;
   }

   public YumOptions withErrorLevel(Integer errorLevel) {
      this.errorLevel = errorLevel;
      return this;
   }
}
