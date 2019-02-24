package com.amazonaws.emr.node.provisioner.config.mapping.operators;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.config.mapping.ConfigMapping;
import com.amazonaws.emr.node.provisioner.config.mapping.ConfigMappingType;

public final class ConfigOverrideMapOperator implements MapOperator {
   public ConfigOverrideMapOperator() {
   }

   public void map(ConfigMapping mapping, Config source, Config destination) throws ConfigException {
      Config config = source.getConfig(mapping.getSourcePath());
      destination.resolveConfig(mapping.getDestinationPath()).mergeWithOverride(config);
   }

   public ConfigMappingType getType() {
      return ConfigMappingType.CONFIG_OVERRIDE;
   }
}
