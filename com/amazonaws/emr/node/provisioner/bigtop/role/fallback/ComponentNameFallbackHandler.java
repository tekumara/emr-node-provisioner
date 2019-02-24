package com.amazonaws.emr.node.provisioner.bigtop.role.fallback;

public final class ComponentNameFallbackHandler implements TranslationFallbackHandler {
   public ComponentNameFallbackHandler() {
   }

   public String handle(String componentName) {
      return componentName;
   }
}
