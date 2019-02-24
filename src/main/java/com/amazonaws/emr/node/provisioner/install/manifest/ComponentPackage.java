package com.amazonaws.emr.node.provisioner.install.manifest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.api.client.util.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.List;

@JsonDeserialize(
   builder = ComponentPackage.Builder.class
)
public final class ComponentPackage {
   private final String componentName;
   private final List<String> packages;
   private final List<String> packageBundles;
   private final List<String> providedPlugins;

   private ComponentPackage(ComponentPackage.Builder builder) {
      this.componentName = builder.componentName;
      this.packages = ImmutableList.copyOf(builder.packages);
      this.packageBundles = ImmutableList.copyOf(builder.packageBundles);
      this.providedPlugins = ImmutableList.copyOf(builder.providedPlugins);
   }

   public String getComponentName() {
      return this.componentName;
   }

   public List<String> getPackages() {
      return this.packages;
   }

   public List<String> getPackageBundles() {
      return this.packageBundles;
   }

   public List<String> getProvidedPlugins() {
      return this.providedPlugins;
   }

   public static ComponentPackage.Builder builder() {
      return new ComponentPackage.Builder();
   }

   @JsonPOJOBuilder
   public static final class Builder {
      private String componentName;
      private List<String> packages = Collections.emptyList();
      private List<String> packageBundles = Collections.emptyList();
      private List<String> providedPlugins = Collections.emptyList();

      public Builder() {
      }

      public ComponentPackage.Builder withComponentName(String componentName) {
         this.componentName = componentName;
         return this;
      }

      public ComponentPackage.Builder withPackages(List<String> packages) {
         this.packages = packages;
         return this;
      }

      public ComponentPackage.Builder withPackageBundles(List<String> packageBundles) {
         this.packageBundles = packageBundles;
         return this;
      }

      public ComponentPackage.Builder withProvidedPlugins(List<String> providedPlugins) {
         this.providedPlugins = providedPlugins;
         return this;
      }

      public ComponentPackage build() {
         Preconditions.checkNotNull(this.componentName, "componentName is required to build ComponentPackage");
         return new ComponentPackage(this);
      }
   }
}
