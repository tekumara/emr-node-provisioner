package com.amazonaws.emr.node.provisioner.bigtop.config.matcher;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import java.util.Set;

public final class AllComponentMatcher implements ComponentMatcher {
   private final Set<String> allComponentNames;

   private AllComponentMatcher(Set<String> allComponentNames) {
      this.allComponentNames = ImmutableSet.copyOf(allComponentNames);
   }

   public static AllComponentMatcher of(Set<String> componentNames) {
      Preconditions.checkNotNull(componentNames, "ComponentNames must not be null");
      Preconditions.checkArgument(!componentNames.isEmpty(), "ComponentNames must not be empty");
      return new AllComponentMatcher(componentNames);
   }

   public boolean match(Set<String> componentNames) {
      Preconditions.checkNotNull(componentNames, "ComponentNames must not be null");
      return componentNames.containsAll(this.allComponentNames);
   }
}
