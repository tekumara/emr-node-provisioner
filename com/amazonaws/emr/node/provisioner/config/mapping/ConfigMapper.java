package com.amazonaws.emr.node.provisioner.config.mapping;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.config.mapping.operators.MapOperator;
import com.amazonaws.emr.node.provisioner.config.mapping.operators.MapOperatorProvider;
import com.amazonaws.emr.node.provisioner.config.mapping.operators.MapOperatorProviders;
import java.util.Collection;
import java.util.Iterator;

public final class ConfigMapper {
   private final Collection<ConfigMappingGroup> groups;
   private final MapOperatorProvider operatorProvider;

   public ConfigMapper(Collection<ConfigMappingGroup> groups) {
      this(groups, MapOperatorProviders.defaultProvider());
   }

   public ConfigMapper(Collection<ConfigMappingGroup> groups, MapOperatorProvider operatorProvider) {
      this.groups = groups;
      this.operatorProvider = operatorProvider;
   }

   public void map(Config source, Config destination) throws ConfigException {
      Iterator var3 = this.groups.iterator();

      while(var3.hasNext()) {
         ConfigMappingGroup group = (ConfigMappingGroup)var3.next();
         MapOperator operator = this.operatorProvider.provide(group.getMappingType());
         Iterator var6 = group.getMappings().iterator();

         while(var6.hasNext()) {
            ConfigMapping mapping = (ConfigMapping)var6.next();
            if (source.hasKey(mapping.getSourcePath())) {
               operator.map(mapping, source, destination);
            }
         }
      }

   }
}
