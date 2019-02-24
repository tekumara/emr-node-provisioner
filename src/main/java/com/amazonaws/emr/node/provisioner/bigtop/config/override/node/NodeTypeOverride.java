package com.amazonaws.emr.node.provisioner.bigtop.config.override.node;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class NodeTypeOverride {
   private final String nodeType;
   private final Config config;

   @JsonCreator
   public NodeTypeOverride(@JsonProperty("nodeType") String nodeType, @JsonProperty("config") Config config) {
      this.nodeType = nodeType;
      this.config = config.deepCopy();
   }

   public String getNodeType() {
      return this.nodeType;
   }

   public Config getConfig() {
      return this.config;
   }
}
