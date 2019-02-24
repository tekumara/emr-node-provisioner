package com.amazonaws.emr.node.provisioner.config.hue;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSortedSet;

public final class HueAppConfigApplier implements ConfigApplier {
   private static final String APP_BLACKLIST_KEY = "hue::server::app_blacklist";
   private static final String APP_INSTALL_KEY = "hue::server::hue_apps";
   private final HueAppOptions hueAppOptions;

   private HueAppConfigApplier(HueAppOptions hueAppOptions) {
      this.hueAppOptions = hueAppOptions;
   }

   public static ConfigApplier using(HueAppOptions hueAppOptions) {
      return new HueAppConfigApplier(hueAppOptions);
   }

   public void applyTo(Config config) throws ConfigException {
      config.put("hue::server::app_blacklist", Joiner.on(",").join(ImmutableSortedSet.copyOf(this.hueAppOptions.getAppsToBlacklist())));
      config.put((String)"hue::server::hue_apps", (Iterable)ImmutableSortedSet.copyOf(this.hueAppOptions.getAppsToInstall()));
   }
}
