package com.amazonaws.emr.node.provisioner.bigtop.hiera;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;

public final class HieraConfigApplier implements ConfigApplier {
   private static final String HIERARCHY_KEY = ":hierarchy";
   private final List<String> additionalDataSources;

   private HieraConfigApplier(List<String> additionalDataSources) {
      this.additionalDataSources = ImmutableList.copyOf(additionalDataSources);
   }

   public static HieraConfigApplier forAddingDataSourceNames(List<String> additionalDataSources) {
      Preconditions.checkNotNull(additionalDataSources, "AdditionalDataSources must not be null");
      return new HieraConfigApplier(additionalDataSources);
   }

   public void applyTo(Config config) throws ConfigException {
      Preconditions.checkNotNull(config, "Config must not be null");
      appendToHierarchy(config, this.additionalDataSources);
   }

   private static void appendToHierarchy(Config config, List<String> dataSources) throws ConfigException {
      List<String> hierarchy = getHierarchy(config);
      hierarchy.addAll(dataSources);
      config.put((String)":hierarchy", (Iterable)hierarchy);
   }

   private static List<String> getHierarchy(Config config) throws ConfigException {
      return (List)(config.hasKey(":hierarchy") ? config.getStringList(":hierarchy") : Lists.newArrayList());
   }
}
