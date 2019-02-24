package com.amazonaws.emr.node.provisioner.config.jobflow;

import com.amazonaws.emr.node.provisioner.pojo.InstanceType;

public interface JobFlowInfo {
   InstanceType getMasterInstanceType();

   InstanceType getCoreInstanceType();

   int getNumInstances();

   boolean isSingleNodeCluster();
}
