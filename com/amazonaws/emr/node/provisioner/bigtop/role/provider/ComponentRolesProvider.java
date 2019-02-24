package com.amazonaws.emr.node.provisioner.bigtop.role.provider;

import com.amazonaws.emr.node.provisioner.bigtop.role.ComponentRole;
import java.util.List;

public interface ComponentRolesProvider {
   List<ComponentRole> provide();
}
