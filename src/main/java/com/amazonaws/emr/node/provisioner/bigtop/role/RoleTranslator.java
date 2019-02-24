package com.amazonaws.emr.node.provisioner.bigtop.role;

import com.amazonaws.emr.node.provisioner.bigtop.role.fallback.TranslationFallbackHandler;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public final class RoleTranslator {
   private final Map<String, ComponentRole> componentRoleMap;
   private final TranslationFallbackHandler fallbackHandler;

   public RoleTranslator(Iterable<ComponentRole> roles, TranslationFallbackHandler fallbackHandler) {
      this.componentRoleMap = buildComponentRoleMap(roles);
      this.fallbackHandler = fallbackHandler;
   }

   public List<String> translate(Iterable<String> componentNames) {
      ArrayList<String> roles = new ArrayList();
      Iterator var3 = componentNames.iterator();

      while(var3.hasNext()) {
         String componentName = (String)var3.next();
         roles.add(this.translate(componentName));
      }

      return roles;
   }

   public String translate(String componentName) {
      ComponentRole role = (ComponentRole)this.componentRoleMap.get(componentName);
      return role == null ? this.fallbackHandler.handle(componentName) : role.getRoleName();
   }

   private static Map<String, ComponentRole> buildComponentRoleMap(Iterable<ComponentRole> roles) {
      return Maps.uniqueIndex(roles, new Function<ComponentRole, String>() {
         @Nullable
         public String apply(ComponentRole role) {
            return role.getComponentName();
         }
      });
   }
}
