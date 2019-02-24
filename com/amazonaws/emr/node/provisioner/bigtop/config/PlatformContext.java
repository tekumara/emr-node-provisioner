package com.amazonaws.emr.node.provisioner.bigtop.config;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigFactory;
import com.amazonaws.emr.node.provisioner.config.jobflow.JobFlowInfo;
import com.amazonaws.emr.node.provisioner.platform.model.Configuration;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PlatformContext {
   private static final String ROOT_MOUNTED_DIR = "/mnt";
   private final String clusterId;
   private final String instanceId;
   private final List<String> componentNames;
   private final Config appConfig;
   private final JobFlowInfo jobFlowInfo;
   private final String nodeType;
   private final List<String> mountedDirs;

   private PlatformContext(PlatformContext.Builder builder) {
      this.clusterId = builder.clusterId;
      this.instanceId = builder.instanceId;
      this.componentNames = ImmutableList.copyOf(builder.componentNames);
      this.appConfig = ConfigFactory.create(builder.configurations);
      this.jobFlowInfo = builder.jobFlowInfo;
      this.nodeType = builder.nodeType;
      this.mountedDirs = builder.mountedDirs;
   }

   public String getClusterId() {
      return this.clusterId;
   }

   public String getInstanceId() {
      return this.instanceId;
   }

   public List<String> getComponentNames() {
      return this.componentNames;
   }

   public Config getAppConfig() {
      return this.appConfig;
   }

   public String getNodeType() {
      return this.nodeType;
   }

   public JobFlowInfo getJobFlowInfo() {
      return this.jobFlowInfo;
   }

   public List<String> getMountedDirs() {
      return this.mountedDirs;
   }

   public List<String> getAppsMountedDirs() {
      List<String> appsMountedDirs = new ArrayList(this.mountedDirs);
      if (appsMountedDirs.size() > 1) {
         appsMountedDirs.remove("/mnt");
      }

      return appsMountedDirs;
   }

   public static PlatformContext.Builder builder() {
      return new PlatformContext.Builder();
   }

   public static final class Builder {
      private String clusterId;
      private String instanceId;
      private List<String> componentNames = Collections.emptyList();
      private List<Configuration> configurations = Collections.emptyList();
      private String nodeType;
      private JobFlowInfo jobFlowInfo;
      private List<String> mountedDirs = Collections.emptyList();

      public Builder() {
      }

      public PlatformContext.Builder withClusterId(String clusterId) {
         this.clusterId = clusterId;
         return this;
      }

      public PlatformContext.Builder withInstanceId(String instanceId) {
         this.instanceId = instanceId;
         return this;
      }

      public PlatformContext.Builder withComponentNames(List<String> componentNames) {
         this.componentNames = componentNames;
         return this;
      }

      public PlatformContext.Builder withConfigurations(List<Configuration> configurations) {
         this.configurations = configurations;
         return this;
      }

      public PlatformContext.Builder withNodeType(String nodeType) {
         this.nodeType = nodeType;
         return this;
      }

      public PlatformContext.Builder withJobFlowInfo(JobFlowInfo jobFlowInfo) {
         this.jobFlowInfo = jobFlowInfo;
         return this;
      }

      public PlatformContext.Builder withMountedDirs(List<String> mountedDirs) {
         this.mountedDirs = mountedDirs;
         return this;
      }

      public PlatformContext build() {
         Preconditions.checkNotNull(this.nodeType, "NodeType is required");
         Preconditions.checkNotNull(this.jobFlowInfo, "JobFlowInfo is required");
         return new PlatformContext(this);
      }
   }
}
