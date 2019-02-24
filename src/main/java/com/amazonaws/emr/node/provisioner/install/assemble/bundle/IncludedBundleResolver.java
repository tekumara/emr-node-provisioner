package com.amazonaws.emr.node.provisioner.install.assemble.bundle;

import com.amazonaws.emr.node.provisioner.install.manifest.PackageBundle;
import com.amazonaws.emr.node.provisioner.install.manifest.PackagePlugin;
import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class IncludedBundleResolver {
   private final Set<String> providedPluginNames;

   public IncludedBundleResolver(Iterable<String> providedPluginNames) {
      this.providedPluginNames = ImmutableSet.copyOf(providedPluginNames);
   }

   public Set<String> resolve(PackageBundle bundle) {
      Set<String> includedBundleNames = new HashSet(bundle.getIncludedBundles());
      Iterator var3 = bundle.getPlugins().iterator();

      while(var3.hasNext()) {
         PackagePlugin packagePlugin = (PackagePlugin)var3.next();
         if (this.providedPluginNames.contains(packagePlugin.getPluginName())) {
            includedBundleNames.addAll(packagePlugin.getPackageBundles());
         }
      }

      return includedBundleNames;
   }
}
