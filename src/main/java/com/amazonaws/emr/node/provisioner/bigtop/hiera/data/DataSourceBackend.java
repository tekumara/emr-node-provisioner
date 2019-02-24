package com.amazonaws.emr.node.provisioner.bigtop.hiera.data;

public enum DataSourceBackend {
   YAML("yaml");

   private final String fileType;

   private DataSourceBackend(String fileType) {
      this.fileType = fileType;
   }

   public String getFileType() {
      return this.fileType;
   }
}
