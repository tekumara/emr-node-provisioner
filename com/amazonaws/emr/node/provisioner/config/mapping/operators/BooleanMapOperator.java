package com.amazonaws.emr.node.provisioner.config.mapping.operators;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.config.mapping.ConfigMapping;
import com.amazonaws.emr.node.provisioner.config.mapping.ConfigMappingType;

public final class BooleanMapOperator implements MapOperator {
   public BooleanMapOperator() {
   }

   public void map(ConfigMapping mapping, Config source, Config destination) throws ConfigException {
      boolean value = source.getBoolean(mapping.getSourcePath());
      destination.put(mapping.getDestinationPath(), value);
   }

   public ConfigMappingType getType() {
      return ConfigMappingType.BOOLEAN;
   }
}
