package com.amazonaws.emr.node.provisioner.bigtop.role.fallback;

public final class DefaultRoleFallbackHandler implements TranslationFallbackHandler {
   private final String defaultRole;

   public DefaultRoleFallbackHandler(String defaultRole) {
      this.defaultRole = defaultRole;
   }

   public String handle(String componentName) {
      return this.defaultRole;
   }
}
