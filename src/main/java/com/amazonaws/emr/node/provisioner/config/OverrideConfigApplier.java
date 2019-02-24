package com.amazonaws.emr.node.provisioner.config;

public final class OverrideConfigApplier implements ConfigApplier {
   private final Config overridingConfig;

   public OverrideConfigApplier(Config overridingConfig) {
      this.overridingConfig = overridingConfig.deepCopy();
   }

   public void applyTo(Config config) throws ConfigException {
      config.mergeWithOverride(this.overridingConfig);
   }
}
