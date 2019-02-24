package com.amazonaws.emr.node.provisioner.bigtop.config.override.node;

import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import java.util.List;

public final class NodeTypeConfigAppliers {
   private static final String NODE_TYPE_OVERRIDES_RESOURCE = "/node-type-overrides.yaml";

   private NodeTypeConfigAppliers() {
   }

   public static ConfigApplier defaultApplier(String nodeType) {
      List<NodeTypeOverride> nodeTypeOverrides = NodeTypeOverrideFactory.createManyFromYamlResource("/node-type-overrides.yaml");
      NodeTypeConfigApplierFactory applierFactory = new NodeTypeConfigApplierFactory(nodeTypeOverrides);
      return applierFactory.create(nodeType);
   }
}
