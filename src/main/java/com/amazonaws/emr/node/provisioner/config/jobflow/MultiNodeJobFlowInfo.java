package com.amazonaws.emr.node.provisioner.config.jobflow;

import com.amazonaws.emr.node.provisioner.pojo.InstanceType;
import org.apache.commons.lang3.builder.ToStringBuilder;

final class MultiNodeJobFlowInfo implements JobFlowInfo {
   private final InstanceType masterInstanceType;
   private final InstanceType coreInstanceType;
   private final int numInstances;

   MultiNodeJobFlowInfo(InstanceType masterInstanceType, InstanceType coreInstanceType, int numInstances) {
      this.masterInstanceType = masterInstanceType;
      this.coreInstanceType = coreInstanceType;
      this.numInstances = numInstances;
   }

   public InstanceType getMasterInstanceType() {
      return this.masterInstanceType;
   }

   public InstanceType getCoreInstanceType() {
      return this.coreInstanceType;
   }

   public int getNumInstances() {
      return this.numInstances;
   }

   public boolean isSingleNodeCluster() {
      return false;
   }

   public String toString() {
      return ToStringBuilder.reflectionToString(this);
   }
}
