package com.amazonaws.emr.node.provisioner.yum.repo;

import com.google.common.collect.ImmutableSet;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;

public final class YumRepoInstaller {
   public static final String DEFAULT_REPO_DIRECTORY_PATH = "/etc/yum.repos.d";
   private final File repoDirectory;

   public YumRepoInstaller() {
      this(new File("/etc/yum.repos.d"));
   }

   public YumRepoInstaller(File repoDirectory) {
      this.repoDirectory = repoDirectory;
   }

   public void install(YumRepo repo) throws IOException {
      File repoFile = this.buildRepoFile(repo);
      YumRepoFile.emptyFile().append(repo).save(repoFile);
      this.setRepoFilePermissions(repoFile);
   }

   private File buildRepoFile(YumRepo repo) {
      return new File(this.repoDirectory, String.format("%s.repo", repo.getId()));
   }

   private void setRepoFilePermissions(File repoFile) throws IOException {
      ImmutableSet<PosixFilePermission> permissions = ImmutableSet.builder().add(PosixFilePermission.OWNER_READ).add(PosixFilePermission.OWNER_WRITE).add(PosixFilePermission.GROUP_READ).add(PosixFilePermission.OTHERS_READ).build();
      Files.setPosixFilePermissions(Paths.get(repoFile.getPath()), permissions);
   }
}
