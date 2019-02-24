package com.amazonaws.emr.node.provisioner.install;

import com.amazonaws.emr.node.provisioner.install.assemble.InstallAssembler;
import com.amazonaws.emr.node.provisioner.install.assemble.InstallAssemblers;
import com.amazonaws.emr.node.provisioner.install.packaging.PackageInstaller;
import com.amazonaws.emr.node.provisioner.install.packaging.PackageResolver;
import com.amazonaws.emr.node.provisioner.install.packaging.YumPackageInstaller;
import com.amazonaws.emr.node.provisioner.util.filter.ListFilter;
import com.amazonaws.emr.node.provisioner.util.filter.PassThroughListFilter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public final class PackageProvisioner {
   private final ListFilter<String> componentsToInstallFilter;
   private final InstallAssembler installAssembler;
   private final PackageInstaller packageInstaller;

   public PackageProvisioner() {
      this(new PassThroughListFilter());
   }

   public PackageProvisioner(ListFilter<String> componentsToInstallFilter) {
      this(componentsToInstallFilter, new YumPackageInstaller());
   }

   public PackageProvisioner(ListFilter<String> componentsToInstallFilter, PackageInstaller packageInstaller) {
      this.componentsToInstallFilter = componentsToInstallFilter;
      this.installAssembler = InstallAssemblers.defaultAssembler();
      this.packageInstaller = packageInstaller;
   }

   public void provision(List<String> componentNames) throws IOException {
      Set<String> providedPlugins = this.installAssembler.assembleProvidedPlugins(componentNames);
      PackageResolver resolver = this.installAssembler.assemblePackageResolver(providedPlugins);
      ComponentInstaller componentInstaller = new ComponentInstaller(this.packageInstaller, resolver);
      componentInstaller.install(this.componentsToInstallFilter.filter(componentNames));
   }
}
