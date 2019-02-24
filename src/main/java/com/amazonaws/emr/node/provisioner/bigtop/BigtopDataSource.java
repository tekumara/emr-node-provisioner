package com.amazonaws.emr.node.provisioner.bigtop;

public enum BigtopDataSource {
   SITE("site.yaml"),
   BIGTOP("bigtop"),
   CLUSTER("bigtop/cluster.yaml");

   private final String name;

   private BigtopDataSource(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }
}
