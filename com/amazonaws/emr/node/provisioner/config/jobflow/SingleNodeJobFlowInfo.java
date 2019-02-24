package com.amazonaws.emr.node.provisioner.config.jobflow;

import com.amazonaws.emr.node.provisioner.pojo.InstanceType;
import org.apache.commons.lang3.builder.ToStringBuilder;

final class SingleNodeJobFlowInfo implements JobFlowInfo {
   private final InstanceType masterInstanceType;

   SingleNodeJobFlowInfo(InstanceType masterInstanceType) {
      this.masterInstanceType = masterInstanceType;
   }

   public InstanceType getMasterInstanceType() {
      return this.masterInstanceType;
   }

   public InstanceType getCoreInstanceType() {
      throw new UnsupportedOperationException("No core instance type for single node clusters");
   }

   public int getNumInstances() {
      return 1;
   }

   public boolean isSingleNodeCluster() {
      return true;
   }

   public String toString() {
      return ToStringBuilder.reflectionToString(this);
   }
}
