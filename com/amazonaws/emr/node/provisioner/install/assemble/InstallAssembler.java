package com.amazonaws.emr.node.provisioner.install.assemble;

import com.amazonaws.emr.node.provisioner.install.assemble.bundle.BundlePackageResolverFactory;
import com.amazonaws.emr.node.provisioner.install.assemble.bundle.IncludedBundleResolver;
import com.amazonaws.emr.node.provisioner.install.assemble.bundle.PackageBundleCatalog;
import com.amazonaws.emr.node.provisioner.install.assemble.component.ComponentPackageCatalog;
import com.amazonaws.emr.node.provisioner.install.assemble.component.ComponentPackageResolverFactory;
import com.amazonaws.emr.node.provisioner.install.manifest.ComponentPackage;
import com.amazonaws.emr.node.provisioner.install.manifest.InstallManifest;
import com.amazonaws.emr.node.provisioner.install.packaging.PackageResolver;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Set;

public final class InstallAssembler {
   private final BundlePackageResolverFactory bundlePackageResolverFactory;
   private final ComponentPackageCatalog componentPackageCatalog;
   private final ComponentPackageResolverFactory componentPackageResolverFactory;

   public InstallAssembler(InstallManifest manifest) {
      this.bundlePackageResolverFactory = buildBundlePackageResolverFactory(manifest);
      this.componentPackageCatalog = new ComponentPackageCatalog(manifest.getComponentPackages());
      this.componentPackageResolverFactory = buildComponentPackageResolverFactory(this.componentPackageCatalog);
   }

   public PackageResolver assemblePackageResolver(Iterable<String> providedPluginNames) {
      IncludedBundleResolver includedBundleResolver = new IncludedBundleResolver(providedPluginNames);
      PackageResolver bundleResolver = this.bundlePackageResolverFactory.create(includedBundleResolver);
      return this.componentPackageResolverFactory.create(bundleResolver);
   }

   public Set<String> assembleProvidedPlugins(Iterable<String> componentNames) {
      Set<String> providedPlugins = Sets.newHashSet();
      Iterator var3 = componentNames.iterator();

      while(var3.hasNext()) {
         String componentName = (String)var3.next();
         if (this.componentPackageCatalog.has(componentName)) {
            ComponentPackage componentPackage = this.componentPackageCatalog.lookUp(componentName);
            providedPlugins.addAll(componentPackage.getProvidedPlugins());
         }
      }

      return providedPlugins;
   }

   private static BundlePackageResolverFactory buildBundlePackageResolverFactory(InstallManifest installManifest) {
      return new BundlePackageResolverFactory(new PackageBundleCatalog(installManifest.getPackageBundles()));
   }

   private static ComponentPackageResolverFactory buildComponentPackageResolverFactory(ComponentPackageCatalog catalog) {
      return new ComponentPackageResolverFactory(catalog);
   }
}
