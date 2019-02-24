package com.amazonaws.emr.node.provisioner.bigtop.role;

import com.amazonaws.emr.node.provisioner.bigtop.role.fallback.ComponentNameFallbackHandler;
import com.amazonaws.emr.node.provisioner.bigtop.role.provider.ComponentRolesProvider;
import com.amazonaws.emr.node.provisioner.bigtop.role.provider.ResourceComponentRolesProvider;

public final class RoleTranslators {
   private static final String DEFAULT_COMPONENT_ROLES_RESOURCE = "/component-roles.yaml";

   private RoleTranslators() {
   }

   public static RoleTranslator defaultTranslator() {
      ComponentRolesProvider provider = new ResourceComponentRolesProvider("/component-roles.yaml");
      return new RoleTranslator(provider.provide(), new ComponentNameFallbackHandler());
   }
}
