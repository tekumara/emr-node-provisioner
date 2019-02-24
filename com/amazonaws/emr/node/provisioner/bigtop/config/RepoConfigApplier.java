package com.amazonaws.emr.node.provisioner.bigtop.config;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import com.amazonaws.emr.node.provisioner.config.ConfigException;

public final class RepoConfigApplier implements ConfigApplier {
   private final String repoUri;
   private final boolean provisionRepo;

   public RepoConfigApplier(String repoUri, boolean provisionRepo) {
      this.repoUri = repoUri;
      this.provisionRepo = provisionRepo;
   }

   public void applyTo(Config config) throws ConfigException {
      config.put(BigtopConfigVar.REPO_URI.getKey(), this.repoUri);
      config.put(BigtopConfigVar.PROVISION_REPO.getKey(), this.provisionRepo);
   }
}
