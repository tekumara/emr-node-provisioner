package com.amazonaws.emr.node.provisioner.bigtop.config.override.node;

import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import com.amazonaws.emr.node.provisioner.config.OverrideConfigApplier;
import com.amazonaws.emr.node.provisioner.config.applier.NoOperationConfigApplier;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public final class NodeTypeConfigApplierFactory {
   private final Map<String, NodeTypeOverride> nodeTypeOverrideMap;

   public NodeTypeConfigApplierFactory(Iterable<NodeTypeOverride> nodeTypeOverrides) {
      this.nodeTypeOverrideMap = buildNodeTypeOverrideMap(nodeTypeOverrides);
   }

   public ConfigApplier create(String nodeType) {
      NodeTypeOverride nodeTypeOverride = (NodeTypeOverride)this.nodeTypeOverrideMap.get(nodeType.toLowerCase());
      return (ConfigApplier)(nodeTypeOverride == null ? new NoOperationConfigApplier() : new OverrideConfigApplier(nodeTypeOverride.getConfig()));
   }

   private static Map<String, NodeTypeOverride> buildNodeTypeOverrideMap(Iterable<NodeTypeOverride> nodeTypeOverrides) {
      return Maps.uniqueIndex(nodeTypeOverrides, new Function<NodeTypeOverride, String>() {
         @Nullable
         public String apply(NodeTypeOverride nodeTypeOverride) {
            return nodeTypeOverride.getNodeType().toLowerCase();
         }
      });
   }
}
