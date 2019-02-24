package com.amazonaws.emr.node.provisioner.bigtop.config;

public enum BigtopConfigVar {
   HADOOP_HEAD_NODE("bigtop::hadoop_head_node"),
   PROVISION_REPO("bigtop::provision_repo"),
   REPO_URI("bigtop::bigtop_repo_uri"),
   ROLES_ENABLED("bigtop::roles_enabled"),
   ROLES("bigtop::roles");

   private final String key;

   private BigtopConfigVar(String key) {
      this.key = key;
   }

   public String getKey() {
      return this.key;
   }
}
