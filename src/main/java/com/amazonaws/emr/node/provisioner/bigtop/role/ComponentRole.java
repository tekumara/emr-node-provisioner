package com.amazonaws.emr.node.provisioner.bigtop.role;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class ComponentRole {
   private final String componentName;
   private final String roleName;

   @JsonCreator
   public ComponentRole(@JsonProperty("componentName") String componentName, @JsonProperty("roleName") String roleName) {
      this.componentName = componentName;
      this.roleName = roleName;
   }

   public String getComponentName() {
      return this.componentName;
   }

   public String getRoleName() {
      return this.roleName;
   }

   public boolean equals(Object object) {
      if (!(object instanceof ComponentRole)) {
         return false;
      } else {
         ComponentRole otherComponentRole = (ComponentRole)object;
         return (new EqualsBuilder()).append(this.componentName, otherComponentRole.componentName).append(this.roleName, otherComponentRole.roleName).isEquals();
      }
   }

   public int hashCode() {
      return (new HashCodeBuilder()).append(this.componentName).append(this.roleName).hashCode();
   }

   public String toString() {
      return (new ToStringBuilder(this)).append("componentName", this.componentName).append("roleName", this.roleName).toString();
   }
}
