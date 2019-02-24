package com.amazonaws.emr.node.provisioner.yum.repo;

import java.io.File;
import java.io.IOException;
import org.apache.commons.lang3.BooleanUtils;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;

public final class YumRepoFile {
   private final Ini ini = new Ini();

   private YumRepoFile() {
      this.ini.getConfig().setStrictOperator(true);
   }

   public YumRepoFile append(YumRepo repo) {
      Section section = this.ini.add(repo.getId());
      if (repo.hasName()) {
         section.put("name", repo.getName());
      }

      section.put("baseurl", repo.getBaseUrl());
      if (repo.hasEnabled()) {
         section.put("enabled", BooleanUtils.toInteger(repo.getEnabled()));
      }

      if (repo.hasGpgCheck()) {
         section.put("gpgcheck", BooleanUtils.toInteger(repo.getGpgCheck()));
      }

      return this;
   }

   public YumRepoFile save(File outputFile) throws IOException {
      this.ini.store(outputFile);
      return this;
   }

   public static YumRepoFile emptyFile() {
      return new YumRepoFile();
   }
}
