package com.amazonaws.emr.node.provisioner;

import java.util.List;

public final class ProgramOptions {
   private final boolean provisionRepo;
   private boolean provision;
   private boolean install;
   private List<String> componentNames;

   private ProgramOptions(boolean provisionRepo, boolean provision, boolean install, List<String> componentNames) {
      this.provisionRepo = provisionRepo;
      this.provision = provision;
      this.install = install;
      this.componentNames = componentNames;
   }

   public boolean isProvisionRepo() {
      return this.provisionRepo;
   }

   public boolean isProvision() {
      return this.provision;
   }

   public boolean isInstall() {
      return this.install;
   }

   public List<String> getComponentNames() {
      return this.componentNames;
   }

   public static final class Builder {
      private boolean provisionRepo;
      private boolean install;
      private boolean provision;
      private List<String> componentNames;

      public Builder() {
      }

      public ProgramOptions.Builder withProvisionRepo(boolean provisionRepo) {
         this.provisionRepo = provisionRepo;
         return this;
      }

      public ProgramOptions.Builder withProvision(boolean provision) {
         this.provision = provision;
         return this;
      }

      public ProgramOptions.Builder withInstall(boolean install) {
         this.install = install;
         return this;
      }

      public ProgramOptions.Builder withComponentNames(List<String> componentNames) {
         this.componentNames = componentNames;
         return this;
      }

      public ProgramOptions build() {
         return new ProgramOptions(this.provisionRepo, this.provision, this.install, this.componentNames);
      }
   }
}
