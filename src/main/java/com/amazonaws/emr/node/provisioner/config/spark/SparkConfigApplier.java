package com.amazonaws.emr.node.provisioner.config.spark;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.config.jobflow.JobFlowInfo;
import com.google.common.base.Preconditions;

public class SparkConfigApplier implements ConfigApplier {
   private final Config appConfig;
   private final JobFlowInfo jobFlowInfo;
   private final SparkResourceAllocator resourceAllocator;

   private SparkConfigApplier(SparkConfigApplier.Builder builder) {
      this.appConfig = builder.appConfig;
      this.jobFlowInfo = builder.jobFlowInfo;
      this.resourceAllocator = builder.resourceAllocator;
   }

   SparkResourceAllocator getResourceAllocator() {
      return this.resourceAllocator;
   }

   public void applyTo(Config config) throws ConfigException {
      Config resourceAllocationConfig = this.resourceAllocator.calculateResourceAllocationConfig(this.appConfig, this.jobFlowInfo);
      Config overrides = config.resolveConfig("spark::common::spark_defaults_overrides");
      overrides.merge(resourceAllocationConfig);
   }

   public static final class Builder {
      private Config appConfig;
      private JobFlowInfo jobFlowInfo;
      private SparkResourceAllocator resourceAllocator;

      public Builder() {
      }

      public SparkConfigApplier.Builder withAppConfig(Config appConfig) {
         this.appConfig = appConfig;
         return this;
      }

      public SparkConfigApplier.Builder withJobFlowInfo(JobFlowInfo jobFlowInfo) {
         this.jobFlowInfo = jobFlowInfo;
         return this;
      }

      public SparkConfigApplier.Builder withResourceAllocator(SparkResourceAllocator resourceAllocator) {
         this.resourceAllocator = resourceAllocator;
         return this;
      }

      public SparkConfigApplier build() {
         Preconditions.checkNotNull(this.appConfig, "AppConfig is required");
         Preconditions.checkNotNull(this.jobFlowInfo, "JobFlowInfo is required");
         Preconditions.checkNotNull(this.resourceAllocator, "ResourceAllocator is required");
         return new SparkConfigApplier(this);
      }
   }
}
