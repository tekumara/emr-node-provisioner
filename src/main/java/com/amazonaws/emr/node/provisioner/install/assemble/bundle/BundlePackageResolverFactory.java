package com.amazonaws.emr.node.provisioner.install.assemble.bundle;

import com.amazonaws.emr.node.provisioner.install.manifest.PackageBundle;
import com.amazonaws.emr.node.provisioner.install.packaging.PackageGroup;
import com.amazonaws.emr.node.provisioner.install.packaging.PackageResolver;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class BundlePackageResolverFactory {
   private final PackageBundleCatalog bundleCatalog;
   private final PackageBundleNameGraphFactory graphFactory;

   public BundlePackageResolverFactory(PackageBundleCatalog bundleCatalog) {
      this.bundleCatalog = bundleCatalog;
      this.graphFactory = new PackageBundleNameGraphFactory(bundleCatalog);
   }

   public PackageResolver create(IncludedBundleResolver includedBundleResolver) {
      Map<String, PackageGroup> visitedGroupMap = new HashMap();
      PackageBundleNameGraph nameGraph = this.graphFactory.create(includedBundleResolver);
      Iterator var4 = nameGraph.listTopologicallyOrdered().iterator();

      while(var4.hasNext()) {
         String bundleName = (String)var4.next();
         PackageBundle bundle = this.bundleCatalog.lookUp(bundleName);
         PackageGroup group = PackageGroup.builder(bundleName).withPackages((Iterable)bundle.getPackages()).withPackages((Iterable)this.collectIncludedBundleNames(nameGraph, visitedGroupMap, bundleName)).build();
         visitedGroupMap.put(bundleName, group);
      }

      return new PackageResolver(visitedGroupMap.values());
   }

   private Set<String> collectIncludedBundleNames(PackageBundleNameGraph nameGraph, Map<String, PackageGroup> visitedGroupMap, String bundleName) {
      Builder<String> builder = ImmutableSet.builder();
      Iterator var5 = nameGraph.getIncludedBundleNames(bundleName).iterator();

      while(var5.hasNext()) {
         String includedBundleName = (String)var5.next();
         Set<String> includedPackages = ((PackageGroup)visitedGroupMap.get(includedBundleName)).getPackages();
         builder.addAll(includedPackages);
      }

      return builder.build();
   }
}
