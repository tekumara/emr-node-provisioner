package com.amazonaws.emr.node.provisioner.config.jobflow;

import com.amazonaws.emr.node.provisioner.pojo.InstanceType;
import com.google.common.base.Preconditions;

public final class JobFlowInfos {
   private JobFlowInfos() {
   }

   public static JobFlowInfo singleNodeCluster(InstanceType instanceType) {
      Preconditions.checkNotNull(instanceType, "instanceType must be non-null");
      return new SingleNodeJobFlowInfo(instanceType);
   }

   public static JobFlowInfo multiNodeCluster(InstanceType masterInstanceType, InstanceType coreInstanceType, int numInstances) {
      Preconditions.checkNotNull(masterInstanceType, "masterInstanceType must be non-null");
      Preconditions.checkNotNull(coreInstanceType, "coreInstanceType must be non-null");
      Preconditions.checkArgument(numInstances > 1, "numInstances must be > 1");
      return new MultiNodeJobFlowInfo(masterInstanceType, coreInstanceType, numInstances);
   }
}
