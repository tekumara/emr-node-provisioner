package com.amazonaws.emr.node.provisioner.puppet.hiera;

import java.io.File;

public final class HieraFileLayout {
   private static final String DEFAULT_BASE_DIRECTORY = "/etc/puppet";
   private static final String CONFIG_FILE_NAME = "hiera.yaml";
   private static final String DATA_DIRECTORY_NAME = "hieradata";
   private final File configFile;
   private final File dataDirectory;

   public HieraFileLayout() {
      this(new File("/etc/puppet"));
   }

   public HieraFileLayout(File baseDirectory) {
      this.configFile = new File(baseDirectory, "hiera.yaml");
      this.dataDirectory = new File(baseDirectory, "hieradata");
   }

   public File getConfigFile() {
      return this.configFile;
   }

   public File getDataDirectory() {
      return this.dataDirectory;
   }

   public File getDataSource(String name) {
      return new File(this.dataDirectory, name);
   }
}
