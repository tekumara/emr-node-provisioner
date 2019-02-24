package com.amazonaws.emr.node.provisioner.bigtop.config;

import com.amazonaws.emr.node.provisioner.config.CompositeConfigApplier;
import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import com.amazonaws.emr.node.provisioner.config.OverrideConfigApplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;

public final class ComponentConfigApplierFactory {
   private final List<ComponentOverride> componentOverrides;

   public ComponentConfigApplierFactory(List<ComponentOverride> configOverrides) {
      this.componentOverrides = ImmutableList.copyOf(configOverrides);
   }

   public ConfigApplier create(List<String> components) {
      CompositeConfigApplier.Builder builder = CompositeConfigApplier.builder();
      Iterator var3 = this.componentOverrides.iterator();

      while(var3.hasNext()) {
         ComponentOverride override = (ComponentOverride)var3.next();
         if (override.getComponentMatcher().match(Sets.newHashSet(components))) {
            builder.addAppliers(new OverrideConfigApplier(override.getConfig()));
         }
      }

      return builder.build();
   }
}
