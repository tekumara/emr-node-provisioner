package com.amazonaws.emr.node.provisioner.bigtop.hiera.data;

import com.google.common.base.Preconditions;

public final class DataSource {
   private final String name;
   private final String content;
   private final DataSourceBackend dataSourceBackend;

   private DataSource(String name, String content, DataSourceBackend dataSourceBackend) {
      this.name = name;
      this.content = content;
      this.dataSourceBackend = dataSourceBackend;
   }

   public static DataSource newInstance(String name, String content, DataSourceBackend dataSourceBackend) {
      Preconditions.checkNotNull(name, "Name must not be null");
      Preconditions.checkNotNull(content, "Content must not be null");
      Preconditions.checkNotNull(dataSourceBackend, "DataSourceBackend must not be null");
      Preconditions.checkArgument(!name.isEmpty(), "Name must not be empty");
      return new DataSource(name, content, dataSourceBackend);
   }

   public String getName() {
      return this.name;
   }

   public String getContent() {
      return this.content;
   }

   public String getFileName() {
      return String.format("%s.%s", this.name, this.dataSourceBackend.getFileType());
   }
}
