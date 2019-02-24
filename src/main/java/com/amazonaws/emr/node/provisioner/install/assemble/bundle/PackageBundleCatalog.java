package com.amazonaws.emr.node.provisioner.install.assemble.bundle;

import com.amazonaws.emr.node.provisioner.install.manifest.PackageBundle;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;

public final class PackageBundleCatalog {
   private final Map<String, PackageBundle> bundleMap;

   public PackageBundleCatalog(Iterable<PackageBundle> bundles) {
      this.bundleMap = buildBundleMap(bundles);
   }

   public Collection<PackageBundle> listAll() {
      return this.bundleMap.values();
   }

   public PackageBundle lookUp(String bundleName) {
      PackageBundle bundle = (PackageBundle)this.bundleMap.get(bundleName);
      if (bundle == null) {
         throw new InvalidPackageBundleNameException("Invalid bundle name: " + bundleName);
      } else {
         return bundle;
      }
   }

   private static Map<String, PackageBundle> buildBundleMap(Iterable<PackageBundle> bundles) {
      return Maps.uniqueIndex(bundles, new Function<PackageBundle, String>() {
         @Nullable
         public String apply(PackageBundle packageBundle) {
            return packageBundle.getBundleName();
         }
      });
   }
}
