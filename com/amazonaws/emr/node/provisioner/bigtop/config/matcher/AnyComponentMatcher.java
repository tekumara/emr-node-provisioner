package com.amazonaws.emr.node.provisioner.bigtop.config.matcher;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Set;

public final class AnyComponentMatcher implements ComponentMatcher {
   private final Set<String> anyComponents;

   private AnyComponentMatcher(Set<String> anyComponents) {
      this.anyComponents = ImmutableSet.copyOf(anyComponents);
   }

   public static AnyComponentMatcher of(Set<String> componentNames) {
      Preconditions.checkNotNull(componentNames, "ComponentNames must not be null");
      Preconditions.checkArgument(!componentNames.isEmpty(), "ComponentNames must not be empty");
      return new AnyComponentMatcher(componentNames);
   }

   public boolean match(Set<String> componentNames) {
      Preconditions.checkNotNull(componentNames, "ComponentNames must not be null");
      return !Sets.intersection(this.anyComponents, componentNames).isEmpty();
   }
}
