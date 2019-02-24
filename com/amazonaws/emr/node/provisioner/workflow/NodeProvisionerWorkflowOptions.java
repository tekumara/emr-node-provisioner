package com.amazonaws.emr.node.provisioner.workflow;

import java.util.List;

public final class NodeProvisionerWorkflowOptions {
   private boolean provisionYumRepo;
   private boolean install;
   private boolean provision;
   private List<String> componentNames;

   public NodeProvisionerWorkflowOptions() {
   }

   public boolean isProvisionYumRepo() {
      return this.provisionYumRepo;
   }

   public NodeProvisionerWorkflowOptions withProvisionYumRepo(boolean provisionYumRepo) {
      this.provisionYumRepo = provisionYumRepo;
      return this;
   }

   public boolean isInstall() {
      return this.install;
   }

   public NodeProvisionerWorkflowOptions withInstall(boolean install) {
      this.install = install;
      return this;
   }

   public boolean isProvision() {
      return this.provision;
   }

   public NodeProvisionerWorkflowOptions withProvision(boolean provision) {
      this.provision = provision;
      return this;
   }

   public List<String> getComponentNames() {
      return this.componentNames;
   }

   public NodeProvisionerWorkflowOptions withComponentNames(List<String> componentNames) {
      this.componentNames = componentNames;
      return this;
   }
}
