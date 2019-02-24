package com.amazonaws.emr.node.provisioner.install.manifest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import java.util.List;

public final class InstallManifest {
   private final List<PackageBundle> packageBundles;
   private final List<ComponentPackage> componentPackages;

   @JsonCreator
   public InstallManifest(@JsonProperty("packageBundles") List<PackageBundle> packageBundles, @JsonProperty("componentPackages") List<ComponentPackage> componentPackages) {
      this.packageBundles = ImmutableList.copyOf(packageBundles);
      this.componentPackages = ImmutableList.copyOf(componentPackages);
   }

   public List<PackageBundle> getPackageBundles() {
      return this.packageBundles;
   }

   public List<ComponentPackage> getComponentPackages() {
      return this.componentPackages;
   }
}
