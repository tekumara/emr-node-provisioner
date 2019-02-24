package com.amazonaws.emr.node.provisioner.config;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.List;

public final class CompositeConfigApplier implements ConfigApplier {
   private final List<ConfigApplier> appliers;

   private CompositeConfigApplier(CompositeConfigApplier.Builder builder) {
      this.appliers = builder.appliersBuilder.build();
   }

   public void applyTo(Config config) throws ConfigException {
      Iterator var2 = this.appliers.iterator();

      while(var2.hasNext()) {
         ConfigApplier applier = (ConfigApplier)var2.next();
         applier.applyTo(config);
      }

   }

   public static CompositeConfigApplier.Builder builder() {
      return new CompositeConfigApplier.Builder();
   }

   public static final class Builder {
      private final com.google.common.collect.ImmutableList.Builder<ConfigApplier> appliersBuilder = ImmutableList.builder();

      public Builder() {
      }

      public CompositeConfigApplier.Builder addAppliers(ConfigApplier... appliers) {
         this.appliersBuilder.add(appliers);
         return this;
      }

      public CompositeConfigApplier.Builder addAppliers(Iterable<ConfigApplier> appliers) {
         this.appliersBuilder.addAll(appliers);
         return this;
      }

      public CompositeConfigApplier build() {
         return new CompositeConfigApplier(this);
      }
   }
}
