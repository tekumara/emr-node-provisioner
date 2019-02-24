package com.amazonaws.emr.node.provisioner.install.manifest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public final class PackagePlugin {
   private final String pluginName;
   private final List<String> packageBundles;

   @JsonCreator
   public PackagePlugin(@JsonProperty("pluginName") String pluginName, @JsonProperty("packageBundles") List<String> packageBundles) {
      this.pluginName = pluginName;
      this.packageBundles = packageBundles;
   }

   public String getPluginName() {
      return this.pluginName;
   }

   public List<String> getPackageBundles() {
      return this.packageBundles;
   }
}
