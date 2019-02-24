package com.amazonaws.emr.node.provisioner.config.spark;

import com.amazonaws.emr.node.provisioner.config.jobflow.JobFlowInfo;
import com.amazonaws.emr.node.provisioner.pojo.InstanceType;

final class SparkConfigUtils {
   private SparkConfigUtils() {
   }

   public static int floorToIncrement(int number, int increment) {
      return number - number % increment;
   }

   public static String formatMemoryValueMB(int memoryValue) {
      return memoryValue + "M";
   }

   public static int getNumberOfInstancesRunningNodeManager(JobFlowInfo info) {
      return info.isSingleNodeCluster() ? 1 : info.getNumInstances() - 1;
   }

   public static int calculateYarnMemoryOverhead(int heapSizeMB) {
      int overheadMB = Math.max(384, (int)((double)heapSizeMB * 0.1D));
      if ((heapSizeMB + overheadMB) % 32 > 0) {
         overheadMB += 32 - (heapSizeMB + overheadMB) % 32;
      }

      return overheadMB;
   }

   public static InstanceType getSmallestInstanceTypeWhereDriversCanRun(JobFlowInfo info) {
      if (info.isSingleNodeCluster()) {
         return info.getMasterInstanceType();
      } else {
         InstanceType masterInstanceType = info.getMasterInstanceType();
         InstanceType coreInstanceType = info.getCoreInstanceType();
         return masterInstanceType.getYarnMaxMemoryMB() < coreInstanceType.getYarnMaxMemoryMB() ? masterInstanceType : coreInstanceType;
      }
   }

   public static InstanceType getInstanceTypeWhereExecutorsCanRun(JobFlowInfo info) {
      return info.isSingleNodeCluster() ? info.getMasterInstanceType() : info.getCoreInstanceType();
   }
}
