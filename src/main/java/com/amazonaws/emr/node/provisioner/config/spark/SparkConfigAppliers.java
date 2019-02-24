package com.amazonaws.emr.node.provisioner.config.spark;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.config.jobflow.JobFlowInfo;
import java.util.List;

public final class SparkConfigAppliers {
   private SparkConfigAppliers() {
   }

   public static SparkConfigApplier getSparkConfigApplierForConfiguration(List<String> componentNames, Config appConfig, JobFlowInfo jobFlowInfo) {
      SparkResourceAllocator resourceAllocator = shouldApplyMaximizedResourceAllocationConfig(componentNames, appConfig) ? new MaximizedSparkResourceAllocator() : new DefaultSparkResourceAllocator();
      return (new SparkConfigApplier.Builder()).withAppConfig(appConfig).withJobFlowInfo(jobFlowInfo).withResourceAllocator((SparkResourceAllocator)resourceAllocator).build();
   }

   private static boolean shouldApplyMaximizedResourceAllocationConfig(List<String> componentNames, Config appConfig) {
      if (!componentNames.contains("spark-on-yarn")) {
         return false;
      } else {
         try {
            if (appConfig.hasKey("spark")) {
               Config sparkConfig = appConfig.getConfig("spark");
               return sparkConfig.hasKey("maximizeResourceAllocation") && sparkConfig.getBoolean("maximizeResourceAllocation");
            }
         } catch (ConfigException var3) {
         }

         return false;
      }
   }
}
