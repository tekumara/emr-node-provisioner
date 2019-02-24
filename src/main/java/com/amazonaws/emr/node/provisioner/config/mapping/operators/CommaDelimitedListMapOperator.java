package com.amazonaws.emr.node.provisioner.config.mapping.operators;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.config.ConfigPath;
import com.amazonaws.emr.node.provisioner.config.mapping.ConfigMapping;
import com.amazonaws.emr.node.provisioner.config.mapping.ConfigMappingType;
import com.google.common.collect.Lists;
import java.util.List;

public final class CommaDelimitedListMapOperator implements MapOperator {
   private static final String DELIMINATOR = ",";

   public CommaDelimitedListMapOperator() {
   }

   public void map(ConfigMapping mapping, Config source, Config destination) throws ConfigException {
      String string = source.getString(mapping.getSourcePath());
      List<String> list = Lists.newArrayList(string.split(","));
      destination.put((ConfigPath)mapping.getDestinationPath(), (Iterable)list);
   }

   public ConfigMappingType getType() {
      return ConfigMappingType.COMMA_DELIMITED_LIST;
   }
}
