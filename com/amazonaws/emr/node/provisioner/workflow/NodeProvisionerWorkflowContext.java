package com.amazonaws.emr.node.provisioner.workflow;

import com.amazonaws.emr.node.provisioner.NodeProvisionerFactory;
import com.amazonaws.emr.node.provisioner.bigtop.config.PlatformContextProvider;
import com.amazonaws.emr.node.provisioner.install.PackageProvisioner;
import com.amazonaws.emr.node.provisioner.install.repo.RepoProvisioner;
import com.amazonaws.emr.node.provisioner.install.repo.RepoUriProvider;
import com.amazonaws.emr.node.provisioner.platform.PlatformNotifier;

public final class NodeProvisionerWorkflowContext {
   private RepoUriProvider repoUriProvider;
   private RepoProvisioner repoProvisioner;
   private PlatformContextProvider platformContextprovider;
   private PackageProvisioner packageProvisioner;
   private NodeProvisionerFactory nodeProvisionerFactory;
   private PlatformNotifier platformNotifier;

   public NodeProvisionerWorkflowContext() {
   }

   public RepoUriProvider getRepoUriProvider() {
      return this.repoUriProvider;
   }

   public NodeProvisionerWorkflowContext withRepoUriProvider(RepoUriProvider repoUriProvider) {
      this.repoUriProvider = repoUriProvider;
      return this;
   }

   public PlatformContextProvider getPlatformContextprovider() {
      return this.platformContextprovider;
   }

   public NodeProvisionerWorkflowContext withPlatformContextprovider(PlatformContextProvider platformContextprovider) {
      this.platformContextprovider = platformContextprovider;
      return this;
   }

   public RepoProvisioner getRepoProvisioner() {
      return this.repoProvisioner;
   }

   public NodeProvisionerWorkflowContext withRepoProvisioner(RepoProvisioner repoProvisioner) {
      this.repoProvisioner = repoProvisioner;
      return this;
   }

   public PackageProvisioner getPackageProvisioner() {
      return this.packageProvisioner;
   }

   public NodeProvisionerWorkflowContext withPackageProvisioner(PackageProvisioner packageProvisioner) {
      this.packageProvisioner = packageProvisioner;
      return this;
   }

   public NodeProvisionerFactory getNodeProvisionerFactory() {
      return this.nodeProvisionerFactory;
   }

   public NodeProvisionerWorkflowContext withNodeProvisionerFactory(NodeProvisionerFactory nodeProvisionerFactory) {
      this.nodeProvisionerFactory = nodeProvisionerFactory;
      return this;
   }

   public PlatformNotifier getPlatformNotifier() {
      return this.platformNotifier;
   }

   public NodeProvisionerWorkflowContext withPlatformNotifier(PlatformNotifier platformNotifier) {
      this.platformNotifier = platformNotifier;
      return this;
   }
}
