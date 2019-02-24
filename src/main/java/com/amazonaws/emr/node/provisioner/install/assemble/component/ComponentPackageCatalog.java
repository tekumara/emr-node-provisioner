package com.amazonaws.emr.node.provisioner.install.assemble.component;

import com.amazonaws.emr.node.provisioner.install.manifest.ComponentPackage;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;

public final class ComponentPackageCatalog {
   private final Map<String, ComponentPackage> bundleMap;

   public ComponentPackageCatalog(Iterable<ComponentPackage> bundles) {
      this.bundleMap = buildBundleMap(bundles);
   }

   public Collection<ComponentPackage> listAll() {
      return this.bundleMap.values();
   }

   public boolean has(String componentName) {
      return this.bundleMap.containsKey(componentName);
   }

   public ComponentPackage lookUp(String componentName) {
      ComponentPackage componentPackage = (ComponentPackage)this.bundleMap.get(componentName);
      if (componentPackage == null) {
         throw new InvalidComponentNameException("Invalid component name: " + componentName);
      } else {
         return componentPackage;
      }
   }

   private static Map<String, ComponentPackage> buildBundleMap(Iterable<ComponentPackage> bundles) {
      return Maps.uniqueIndex(bundles, new Function<ComponentPackage, String>() {
         @Nullable
         public String apply(ComponentPackage componentPackage) {
            return componentPackage.getComponentName();
         }
      });
   }
}
