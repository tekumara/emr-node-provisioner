package com.amazonaws.emr.node.provisioner.config.hue;

import com.amazonaws.emr.node.provisioner.config.hue.behavior.ComponentBehavior;
import com.amazonaws.emr.node.provisioner.config.hue.behavior.HueAppBehavior;
import com.amazonaws.emr.node.provisioner.config.hue.behavior.HueAppVisibility;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class DefaultHueAppOptionsResolver implements HueAppOptionsResolver {
   private final List<HueAppBehavior> appBehaviors;

   private DefaultHueAppOptionsResolver(List<HueAppBehavior> appBehaviors) {
      this.appBehaviors = ImmutableList.copyOf(appBehaviors);
   }

   public static DefaultHueAppOptionsResolver newInstance(List<HueAppBehavior> appBehaviors) {
      return new DefaultHueAppOptionsResolver(appBehaviors);
   }

   public HueAppOptions resolve(List<String> componentNames) {
      return this.resolveWithSet(Sets.newHashSet(componentNames));
   }

   private HueAppOptions resolveWithSet(Set<String> componentNames) {
      Set<String> appsToBlacklist = Sets.newHashSet();
      Set<String> appsToInstall = Sets.newHashSet();
      Iterator var4 = this.appBehaviors.iterator();

      while(var4.hasNext()) {
         HueAppBehavior appBehavior = (HueAppBehavior)var4.next();
         HueAppVisibility desiredVisibility = this.resolveDesiredVisibility(appBehavior, componentNames);
         switch(desiredVisibility) {
         case REMOVED:
            if (!appBehavior.hasSeparatePackage()) {
               appsToBlacklist.add(appBehavior.getAppName());
            }
            break;
         case VISIBLE:
            if (appBehavior.hasSeparatePackage()) {
               appsToInstall.add(appBehavior.getAppName());
            }
            break;
         default:
            throw new IllegalStateException(String.format("Unsupported visibility %s for hue app %s", desiredVisibility, appBehavior.getAppName()));
         }
      }

      return HueAppOptions.newInstance(appsToBlacklist, appsToInstall);
   }

   private HueAppVisibility resolveDesiredVisibility(HueAppBehavior appBehavior, Set<String> componentNames) {
      HueAppVisibility desiredVisibility = appBehavior.getDefaultVisibility();
      Iterator var4 = appBehavior.getComponentBehaviors().iterator();

      while(var4.hasNext()) {
         ComponentBehavior componentBehavior = (ComponentBehavior)var4.next();
         if (componentNames.contains(componentBehavior.getComponentName())) {
            desiredVisibility = HueAppVisibility.maximum(desiredVisibility, componentBehavior.getMinimumVisibility());
         }
      }

      return desiredVisibility;
   }
}
