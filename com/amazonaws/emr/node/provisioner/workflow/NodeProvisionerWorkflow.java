package com.amazonaws.emr.node.provisioner.workflow;

import com.amazonaws.emr.node.provisioner.NodeProvisioner;
import com.amazonaws.emr.node.provisioner.NodeProvisionerFactory;
import com.amazonaws.emr.node.provisioner.bigtop.config.PlatformConfigApplierFactory;
import com.amazonaws.emr.node.provisioner.bigtop.config.PlatformContext;
import com.amazonaws.emr.node.provisioner.bigtop.config.PlatformContextProvider;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.install.PackageProvisioner;
import com.amazonaws.emr.node.provisioner.install.repo.RepoProvisioner;
import com.amazonaws.emr.node.provisioner.install.repo.RepoUriProvider;
import com.amazonaws.emr.node.provisioner.install.repo.YumRepoProvisioner;
import com.amazonaws.emr.node.provisioner.platform.PlatformNotifier;
import com.amazonaws.emr.node.provisioner.puppet.api.PuppetException;
import com.amazonaws.emr.node.provisioner.util.filter.ListFilter;
import com.amazonaws.emr.node.provisioner.util.filter.PassThroughListFilter;
import com.google.common.annotations.VisibleForTesting;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NodeProvisionerWorkflow {
   private static final Logger LOGGER = LoggerFactory.getLogger(NodeProvisionerWorkflow.class);
   private RepoUriProvider repoUriProvider;
   private RepoProvisioner repoProvisioner;
   private PackageProvisioner packageProvisioner;
   private PlatformContextProvider platformContextprovider;
   private NodeProvisionerFactory nodeProvisionerFactory;
   private PlatformNotifier platformNotifier;

   public NodeProvisionerWorkflow() {
      ListFilter<String> componentFilter = new PassThroughListFilter();
      PackageProvisioner packageProvisioner = new PackageProvisioner(componentFilter);
      NodeProvisionerFactory nodeProvisionerFactory = NodeProvisionerFactory.builder().withApplierFactory(PlatformConfigApplierFactory.builder().withComponentFilter(componentFilter).build()).build();
      NodeProvisionerWorkflowContext nodeProvisionerWorkflowContext = (new NodeProvisionerWorkflowContext()).withRepoUriProvider(new RepoUriProvider()).withRepoProvisioner(new YumRepoProvisioner()).withPackageProvisioner(packageProvisioner).withPlatformContextprovider(new PlatformContextProvider()).withNodeProvisionerFactory(nodeProvisionerFactory).withPlatformNotifier(new PlatformNotifier());
      this.initialize(nodeProvisionerWorkflowContext);
   }

   @VisibleForTesting
   NodeProvisionerWorkflow(NodeProvisionerWorkflowContext nodeProvisionerWorkflowContext) {
      this.initialize(nodeProvisionerWorkflowContext);
   }

   private void initialize(NodeProvisionerWorkflowContext nodeProvisionerWorkflowContext) {
      this.repoUriProvider = nodeProvisionerWorkflowContext.getRepoUriProvider();
      this.repoProvisioner = nodeProvisionerWorkflowContext.getRepoProvisioner();
      this.packageProvisioner = nodeProvisionerWorkflowContext.getPackageProvisioner();
      this.platformContextprovider = nodeProvisionerWorkflowContext.getPlatformContextprovider();
      this.nodeProvisionerFactory = nodeProvisionerWorkflowContext.getNodeProvisionerFactory();
      this.platformNotifier = nodeProvisionerWorkflowContext.getPlatformNotifier();
   }

   public void work(NodeProvisionerWorkflowOptions phaseWorkflowOptions) throws IOException, ConfigException, PuppetException {
      try {
         this.doWork(phaseWorkflowOptions);
         if (!phaseWorkflowOptions.isProvision()) {
            LOGGER.info("ProvisionAppsPhase invoked without --provision flag. Ignoring platform notification.");
         } else {
            try {
               this.platformNotifier.notifySuccess();
            } catch (Exception var3) {
               LOGGER.error("Success notification to platform failed", var3);
            }

         }
      } catch (Exception var4) {
         this.notifyFailure(var4);
         throw var4;
      }
   }

   private void notifyFailure(Exception exception) {
      try {
         this.platformNotifier.notifyFailure(exception);
      } catch (Exception var3) {
         LOGGER.error("Failure notification to platform failed", var3);
      }

   }

   private void doWork(NodeProvisionerWorkflowOptions phaseWorkflowOptions) throws PuppetException, IOException, ConfigException {
      String repoUri = this.repoUriProvider.provide();
      if (phaseWorkflowOptions.isProvisionYumRepo()) {
         LOGGER.info("Provisioning Yum repo with repoUri: {}", repoUri);
         this.repoProvisioner.provision(repoUri);
      }

      List<String> componentNames = phaseWorkflowOptions.getComponentNames();
      PlatformContext platformContext = null;
      if (phaseWorkflowOptions.isInstall()) {
         if (componentNames == null || componentNames.isEmpty()) {
            LOGGER.info("No componentNames passed. Fetching componentNames from platform");
            platformContext = this.platformContextprovider.provide();
            componentNames = platformContext.getComponentNames();
         }

         LOGGER.info("Installing components: {}", componentNames);
         this.packageProvisioner.provision(componentNames);
      }

      if (phaseWorkflowOptions.isProvision()) {
         LOGGER.info("Provisioning components");
         if (platformContext == null) {
            platformContext = this.platformContextprovider.provide();
         }

         NodeProvisioner nodeProvisioner = this.nodeProvisionerFactory.create(repoUri, platformContext, phaseWorkflowOptions.isProvisionYumRepo());
         nodeProvisioner.provision();
      }

   }
}
