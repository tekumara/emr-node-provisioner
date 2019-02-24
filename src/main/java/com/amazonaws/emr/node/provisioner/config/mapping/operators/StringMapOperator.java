package com.amazonaws.emr.node.provisioner.config.mapping.operators;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.config.mapping.ConfigMapping;
import com.amazonaws.emr.node.provisioner.config.mapping.ConfigMappingType;

public final class StringMapOperator implements MapOperator {
   public StringMapOperator() {
   }

   public void map(ConfigMapping mapping, Config source, Config destination) throws ConfigException {
      String string = source.getString(mapping.getSourcePath());
      destination.put(mapping.getDestinationPath(), string);
   }

   public ConfigMappingType getType() {
      return ConfigMappingType.STRING;
   }
}
