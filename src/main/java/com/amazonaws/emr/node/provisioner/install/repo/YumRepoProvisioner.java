package com.amazonaws.emr.node.provisioner.install.repo;

import com.amazonaws.emr.node.provisioner.yum.repo.YumRepo;
import com.amazonaws.emr.node.provisioner.yum.repo.YumRepoInstaller;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class YumRepoProvisioner implements RepoProvisioner {
   private static final Logger logger = LoggerFactory.getLogger(YumRepoProvisioner.class);
   private final YumRepoInstaller repoInstaller;

   public YumRepoProvisioner() {
      this(new YumRepoInstaller());
   }

   public YumRepoProvisioner(YumRepoInstaller repoInstaller) {
      this.repoInstaller = repoInstaller;
   }

   public void provision(String repoUri) {
      YumRepo repo = YumRepo.builder().withId("Bigtop").withName("Bigtop packages").withBaseUrl(repoUri).withEnabled(true).withGpgCheck(false).build();

      try {
         this.repoInstaller.install(repo);
      } catch (IOException var4) {
         throw new RuntimeException(String.format("Failed to install %s yum repo", repo.getId()));
      }
   }
}
