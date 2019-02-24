package com.amazonaws.emr.node.provisioner.bigtop.role.provider;

import com.amazonaws.emr.node.provisioner.bigtop.role.ComponentRole;
import com.amazonaws.emr.node.provisioner.bigtop.role.ComponentRoleFactory;
import com.amazonaws.emr.node.provisioner.util.ResourceUtil;
import java.io.IOException;
import java.util.List;

public final class ResourceComponentRolesProvider implements ComponentRolesProvider {
   private final String resourceName;

   public ResourceComponentRolesProvider(String resourceName) {
      this.resourceName = resourceName;
   }

   public List<ComponentRole> provide() {
      try {
         return ComponentRoleFactory.createManyFromYaml(ResourceUtil.extractToString(this.resourceName));
      } catch (IOException var2) {
         throw new RuntimeException(String.format("Failed to provide ConfigRoles from resource %s", this.resourceName), var2);
      }
   }
}
