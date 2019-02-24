package com.amazonaws.emr.node.provisioner.install.assemble.component;

import com.amazonaws.emr.node.provisioner.install.manifest.ComponentPackage;
import com.amazonaws.emr.node.provisioner.install.packaging.PackageGroup;
import com.amazonaws.emr.node.provisioner.install.packaging.PackageResolver;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Iterator;
import java.util.Set;

public final class ComponentPackageResolverFactory {
   private final ComponentPackageCatalog packageCatalog;

   public ComponentPackageResolverFactory(ComponentPackageCatalog packageCatalog) {
      this.packageCatalog = packageCatalog;
   }

   public PackageResolver create(PackageResolver bundlePackageResolver) {
      Builder<PackageGroup> packageGroups = ImmutableList.builder();
      Iterator var3 = this.packageCatalog.listAll().iterator();

      while(var3.hasNext()) {
         ComponentPackage componentPackage = (ComponentPackage)var3.next();
         Set<String> bundlePackages = bundlePackageResolver.resolve(componentPackage.getPackageBundles());
         packageGroups.add(PackageGroup.builder(componentPackage.getComponentName()).withPackages((Iterable)componentPackage.getPackages()).withPackages((Iterable)bundlePackages).build());
      }

      return new PackageResolver(packageGroups.build());
   }
}
