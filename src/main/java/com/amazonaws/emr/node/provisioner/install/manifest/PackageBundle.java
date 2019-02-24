package com.amazonaws.emr.node.provisioner.install.manifest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.api.client.util.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@JsonDeserialize(
   builder = PackageBundle.Builder.class
)
public final class PackageBundle {
   private final String bundleName;
   private final Set<String> packages;
   private final Set<String> includedBundles;
   private final List<PackagePlugin> plugins;

   private PackageBundle(PackageBundle.Builder builder) {
      this.bundleName = builder.bundleName;
      this.packages = ImmutableSet.copyOf(builder.packages);
      this.includedBundles = ImmutableSet.copyOf(builder.includedBundles);
      this.plugins = ImmutableList.copyOf(builder.plugins);
   }

   public String getBundleName() {
      return this.bundleName;
   }

   public Set<String> getPackages() {
      return this.packages;
   }

   public Set<String> getIncludedBundles() {
      return this.includedBundles;
   }

   public List<PackagePlugin> getPlugins() {
      return this.plugins;
   }

   public static PackageBundle.Builder builder() {
      return new PackageBundle.Builder();
   }

   @JsonPOJOBuilder
   public static final class Builder {
      private String bundleName;
      private Iterable<String> packages = Collections.emptySet();
      private Iterable<String> includedBundles = Collections.emptySet();
      private List<PackagePlugin> plugins = Collections.emptyList();

      public Builder() {
      }

      public PackageBundle.Builder withBundleName(String bundleName) {
         this.bundleName = bundleName;
         return this;
      }

      public PackageBundle.Builder withPackages(Iterable<String> packages) {
         this.packages = packages;
         return this;
      }

      public PackageBundle.Builder withIncludedBundles(Iterable<String> includedBundles) {
         this.includedBundles = includedBundles;
         return this;
      }

      public PackageBundle.Builder withPlugins(List<PackagePlugin> plugins) {
         this.plugins = plugins;
         return this;
      }

      public PackageBundle build() {
         Preconditions.checkNotNull(this.bundleName, "bundleName is required to build PackageBundle");
         return new PackageBundle(this);
      }
   }
}
