package com.amazonaws.emr.node.provisioner.install.repo;

public final class RepoUriProvider {
   private static final String BIGTOP_REPO_URI = "file:///var/aws/emr/packages/bigtop";
   private final String repoUri;

   public RepoUriProvider() {
      this("file:///var/aws/emr/packages/bigtop");
   }

   public RepoUriProvider(String repoUri) {
      this.repoUri = repoUri;
   }

   public String provide() {
      return this.repoUri;
   }
}
