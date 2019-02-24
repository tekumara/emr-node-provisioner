package com.amazonaws.emr.node.provisioner.config.mapping.operators;

public final class MapOperatorProviders {
   private MapOperatorProviders() {
   }

   public static MapOperatorProvider defaultProvider() {
      return defaultProviderWithOverrides();
   }

   public static MapOperatorProvider defaultProviderWithOverrides(MapOperator... overrides) {
      return (new MapOperatorProvider.Builder()).register(new CommaDelimitedListMapOperator()).register(new StringMapOperator()).register(new BooleanMapOperator()).register(new ConfigOverrideMapOperator()).register(new DnsReverseLookupMapOperator()).register(new StaticBasePathMapOperator()).register(overrides).build();
   }
}
