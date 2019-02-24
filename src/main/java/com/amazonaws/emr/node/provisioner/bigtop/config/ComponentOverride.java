package com.amazonaws.emr.node.provisioner.bigtop.config;

import com.amazonaws.emr.node.provisioner.bigtop.config.matcher.AllComponentMatcher;
import com.amazonaws.emr.node.provisioner.bigtop.config.matcher.ComponentMatcher;
import com.amazonaws.emr.node.provisioner.bigtop.config.matcher.ComponentMatcherDeserializer;
import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.util.Optionals;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

@JsonDeserialize(
   builder = ComponentOverride.Builder.class
)
public final class ComponentOverride {
   private final ComponentMatcher componentMatcher;
   private final Config config;

   private ComponentOverride(ComponentOverride.Builder builder) {
      if (builder.componentName.isPresent()) {
         this.componentMatcher = AllComponentMatcher.of(Sets.newHashSet(new String[]{(String)builder.componentName.get()}));
      } else {
         this.componentMatcher = (ComponentMatcher)builder.componentMatcher.get();
      }

      this.config = builder.config;
   }

   public static ComponentOverride.Builder builder() {
      return new ComponentOverride.Builder();
   }

   public ComponentMatcher getComponentMatcher() {
      return this.componentMatcher;
   }

   public Config getConfig() {
      return this.config;
   }

   @JsonPOJOBuilder
   public static final class Builder {
      private Optional<String> componentName;
      private Optional<ComponentMatcher> componentMatcher;
      private Config config;

      private Builder() {
         this.componentName = Optional.absent();
         this.componentMatcher = Optional.absent();
      }

      public ComponentOverride.Builder withComponentName(String componentName) {
         this.componentName = Optional.of(componentName);
         return this;
      }

      @JsonDeserialize(
         using = ComponentMatcherDeserializer.class
      )
      public ComponentOverride.Builder withComponentMatcher(ComponentMatcher componentMatcher) {
         this.componentMatcher = Optional.of(componentMatcher);
         return this;
      }

      public ComponentOverride.Builder withConfig(Config config) {
         this.config = config;
         return this;
      }

      public ComponentOverride build() {
         Preconditions.checkArgument(Optionals.isOnlyOnePresent(this.componentName, this.componentMatcher), "Only one of ComponentName or ComponentMatcher must be given");
         Preconditions.checkNotNull(this.config, "Config is required");
         return new ComponentOverride(this);
      }
   }
}
