package com.amazonaws.emr.node.provisioner.config.spark;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.config.jobflow.JobFlowInfo;
import com.amazonaws.emr.node.provisioner.pojo.InstanceType;

public final class MaximizedSparkResourceAllocator implements SparkResourceAllocator {
   public MaximizedSparkResourceAllocator() {
   }

   public Config calculateResourceAllocationConfig(Config appConfig, JobFlowInfo jobFlowInfo) throws ConfigException {
      Config config = Config.empty();
      InstanceType executorInstanceType = SparkConfigUtils.getInstanceTypeWhereExecutorsCanRun(jobFlowInfo);
      InstanceType smallestDriverInstanceType = SparkConfigUtils.getSmallestInstanceTypeWhereDriversCanRun(jobFlowInfo);
      int instancesRunningNodeManager = SparkConfigUtils.getNumberOfInstancesRunningNodeManager(jobFlowInfo);
      int executorInstances = instancesRunningNodeManager * 1;
      int executorCores = executorInstanceType.getVcores();
      int defaultParallelism = Math.max(2, executorInstances * executorCores * 2);
      HeapAndOverhead driverHeapAndOverhead = calculateDriverHeapAndOverhead(smallestDriverInstanceType, instancesRunningNodeManager);
      HeapAndOverhead amHeapAndOverhead = calculateApplicationMasterHeapAndOverhead(driverHeapAndOverhead);
      HeapAndOverhead executorHeapAndOverhead = calculateExecutorHeapAndOverhead(executorInstanceType, amHeapAndOverhead, driverHeapAndOverhead, instancesRunningNodeManager);
      if (!isDynamicAllocationEnabledInAppConfig(appConfig)) {
         config.put("spark.executor.instances", Integer.toString(executorInstances));
      }

      config.put("spark.executor.cores", Integer.toString(executorCores));
      config.put("spark.driver.memory", SparkConfigUtils.formatMemoryValueMB(driverHeapAndOverhead.heapSizeMB));
      config.put("spark.executor.memory", SparkConfigUtils.formatMemoryValueMB(executorHeapAndOverhead.heapSizeMB));
      if (amHeapAndOverhead.heapSizeMB != 512) {
         config.put("spark.yarn.am.memory", SparkConfigUtils.formatMemoryValueMB(amHeapAndOverhead.heapSizeMB));
      }

      config.put("spark.default.parallelism", Integer.toString(defaultParallelism));
      return config;
   }

   private static HeapAndOverhead calculateDriverHeapAndOverhead(InstanceType smallestDriverInstanceType, int instancesRunningNodeManager) {
      if (shouldMaximizeDriverMemory(smallestDriverInstanceType, instancesRunningNodeManager)) {
         return calculateHeapAndOverheadForInstanceType(smallestDriverInstanceType);
      } else {
         int driverHeapSizeMB = Math.min(1024, smallestDriverInstanceType.getYarnMaxMemoryMB() / 2 - 384);
         int driverOverheadMB = SparkConfigUtils.calculateYarnMemoryOverhead(driverHeapSizeMB);
         return new HeapAndOverhead(driverHeapSizeMB, driverOverheadMB);
      }
   }

   private static boolean shouldMaximizeDriverMemory(InstanceType smallestDriverInstanceType, int instancesRunningNodeManager) {
      return instancesRunningNodeManager > 1 && smallestDriverInstanceType.getYarnMaxMemoryMB() > 1408;
   }

   private static HeapAndOverhead calculateApplicationMasterHeapAndOverhead(HeapAndOverhead driverHeapAndOverhead) {
      return driverHeapAndOverhead.heapSizeMB < 512 ? driverHeapAndOverhead : new HeapAndOverhead(512, 384);
   }

   private static HeapAndOverhead calculateExecutorHeapAndOverhead(InstanceType executorInstanceType, HeapAndOverhead amHeapAndOverhead, HeapAndOverhead driverHeapAndOverhead, int instancesRunningNodeManager) {
      int reservedMemory = instancesRunningNodeManager > 1 ? amHeapAndOverhead.heapSizeMB + amHeapAndOverhead.overheadMB : driverHeapAndOverhead.heapSizeMB + driverHeapAndOverhead.overheadMB;
      return calculateHeapAndOverheadForInstanceType(executorInstanceType, reservedMemory);
   }

   private static HeapAndOverhead calculateHeapAndOverheadForInstanceType(InstanceType instanceType) {
      return calculateHeapAndOverheadForInstanceType(instanceType, 0);
   }

   private static HeapAndOverhead calculateHeapAndOverheadForInstanceType(InstanceType instanceType, int reservedMemory) {
      int yarnMaxMemoryMB = instanceType.getYarnMaxMemoryMB();
      int availMemoryMB = SparkConfigUtils.floorToIncrement(yarnMaxMemoryMB - reservedMemory, 32);
      int heapSizeMB;
      if ((double)availMemoryMB * 0.1D / 1.1D < 384.0D) {
         heapSizeMB = availMemoryMB - 384;
      } else {
         heapSizeMB = Math.max(465, (int)Math.round((double)availMemoryMB / 1.1D));
      }

      return new HeapAndOverhead(heapSizeMB, SparkConfigUtils.calculateYarnMemoryOverhead(heapSizeMB));
   }

   private static boolean isDynamicAllocationEnabledInAppConfig(Config appConfig) throws ConfigException {
      if (appConfig.hasKey("spark-defaults")) {
         Config sparkDefaultsAppConfig = appConfig.resolveConfig("spark-defaults");
         if (sparkDefaultsAppConfig.hasKey("spark.dynamicAllocation.enabled")) {
            return sparkDefaultsAppConfig.getBoolean("spark.dynamicAllocation.enabled");
         }
      }

      return false;
   }
}
