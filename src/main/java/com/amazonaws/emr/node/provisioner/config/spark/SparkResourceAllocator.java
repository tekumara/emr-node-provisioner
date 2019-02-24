package com.amazonaws.emr.node.provisioner.config.spark;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.config.jobflow.JobFlowInfo;

public interface SparkResourceAllocator {
   Config calculateResourceAllocationConfig(Config var1, JobFlowInfo var2) throws ConfigException;
}
