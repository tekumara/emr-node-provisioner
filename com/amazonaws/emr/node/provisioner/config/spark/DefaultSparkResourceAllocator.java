package com.amazonaws.emr.node.provisioner.config.spark;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.config.jobflow.JobFlowInfo;
import com.amazonaws.emr.node.provisioner.pojo.InstanceType;

public final class DefaultSparkResourceAllocator implements SparkResourceAllocator {
   public DefaultSparkResourceAllocator() {
   }

   public Config calculateResourceAllocationConfig(Config appConfig, JobFlowInfo jobFlowInfo) throws ConfigException {
      Config resourceAllocationConfig = Config.empty();
      InstanceType executorInstanceType = SparkConfigUtils.getInstanceTypeWhereExecutorsCanRun(jobFlowInfo);
      int executorMemory = pickOptimalExecutorMemory(executorInstanceType);
      int executorCores = pickOptimalExecutorCores(executorInstanceType);
      resourceAllocationConfig.put("spark.executor.memory", SparkConfigUtils.formatMemoryValueMB(executorMemory));
      resourceAllocationConfig.put("spark.executor.cores", Integer.toString(executorCores));
      return resourceAllocationConfig;
   }

   private static int pickOptimalExecutorMemory(InstanceType instanceType) {
      return instanceType.getSparkOptimalExecutorMemory();
   }

   private static int pickOptimalExecutorCores(InstanceType instanceType) {
      return instanceType.getSparkOptimalExecutorCores();
   }
}
