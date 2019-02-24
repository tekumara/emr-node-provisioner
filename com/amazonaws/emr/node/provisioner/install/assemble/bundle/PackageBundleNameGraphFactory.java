package com.amazonaws.emr.node.provisioner.install.assemble.bundle;

import com.amazonaws.emr.node.provisioner.install.manifest.PackageBundle;
import java.util.Iterator;
import java.util.Set;

public final class PackageBundleNameGraphFactory {
   private final PackageBundleCatalog packageBundleCatalog;

   public PackageBundleNameGraphFactory(PackageBundleCatalog packageBundleCatalog) {
      this.packageBundleCatalog = packageBundleCatalog;
   }

   public PackageBundleNameGraph create(IncludedBundleResolver includedBundleResolver) {
      PackageBundleNameGraph.Builder builder = PackageBundleNameGraph.builder();
      Iterator var3 = this.packageBundleCatalog.listAll().iterator();

      while(var3.hasNext()) {
         PackageBundle bundle = (PackageBundle)var3.next();
         Set<String> includedBundleNames = includedBundleResolver.resolve(bundle);
         builder.addBundle(bundle.getBundleName(), includedBundleNames);
      }

      return builder.build();
   }
}
