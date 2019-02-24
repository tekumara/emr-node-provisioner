package com.amazonaws.emr.node.provisioner.install.packaging;

import com.google.common.collect.ImmutableSet;
import java.util.Arrays;
import java.util.Set;

public final class PackageGroup {
   private final String groupName;
   private final Set<String> packages;

   private PackageGroup(PackageGroup.Builder builder) {
      this.groupName = builder.groupName;
      this.packages = builder.packagesBuilder.build();
   }

   public String getGroupName() {
      return this.groupName;
   }

   public Set<String> getPackages() {
      return this.packages;
   }

   public static PackageGroup.Builder builder(String groupName) {
      return new PackageGroup.Builder(groupName);
   }

   public static final class Builder {
      private final String groupName;
      private final com.google.common.collect.ImmutableSet.Builder<String> packagesBuilder;

      public Builder(String groupName) {
         this.groupName = groupName;
         this.packagesBuilder = ImmutableSet.builder();
      }

      public PackageGroup.Builder withPackages(String... packages) {
         this.withPackages((Iterable)Arrays.asList(packages));
         return this;
      }

      public PackageGroup.Builder withPackages(Iterable<String> packages) {
         this.packagesBuilder.addAll(packages);
         return this;
      }

      public PackageGroup build() {
         return new PackageGroup(this);
      }
   }
}
