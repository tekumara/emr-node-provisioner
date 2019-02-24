package com.amazonaws.emr.node.provisioner.platform.model;

import com.google.api.client.util.Key;
import java.util.List;

public final class GetConfigurationResponse {
   @Key
   private List<Configuration> configurations;
   @Key
   private List<String> componentNames;
   @Key
   private String clusterId;
   @Key
   private String instanceId;
   @Key
   private String instanceType;
   @Key
   private String nodeType;
   @Key
   private String releaseLabel;
   @Key
   private List<String> mountedDirs;

   public GetConfigurationResponse() {
   }

   public List<Configuration> getConfigurations() {
      return this.configurations;
   }

   public List<String> getComponentNames() {
      return this.componentNames;
   }

   public String getClusterId() {
      return this.clusterId;
   }

   public String getInstanceId() {
      return this.instanceId;
   }

   public String getInstanceType() {
      return this.instanceType;
   }

   public String getNodeType() {
      return this.nodeType;
   }

   public String getReleaseLabel() {
      return this.releaseLabel;
   }

   public List<String> getMountedDirs() {
      return this.mountedDirs;
   }
}
