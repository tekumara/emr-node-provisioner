package com.amazonaws.emr.node.provisioner.config.hue;

import com.amazonaws.emr.node.provisioner.config.hue.behavior.HueAppBehavior;
import com.amazonaws.emr.node.provisioner.config.hue.behavior.provider.HueAppBehaviorProvider;
import com.amazonaws.emr.node.provisioner.config.hue.behavior.provider.ResourceHueAppBehaviorProvider;
import java.util.List;

public final class HueAppOptionsResolvers {
   private static final String DEFAULT_APP_BEHAVIOURS_RESOURCE = "/config/applications/hue/hue-app-behaviors.yaml";

   private HueAppOptionsResolvers() {
   }

   public static HueAppOptionsResolver defaultResolver() {
      HueAppBehaviorProvider behaviorProvider = ResourceHueAppBehaviorProvider.using("/config/applications/hue/hue-app-behaviors.yaml");
      List<HueAppBehavior> appBehaviors = behaviorProvider.provide();
      return DefaultHueAppOptionsResolver.newInstance(appBehaviors);
   }
}
