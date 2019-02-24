package com.amazonaws.emr.node.provisioner.config.hue.behavior;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class ComponentBehavior {
   private final String componentName;
   private final HueAppVisibility minimumVisibility;

   @JsonCreator
   public ComponentBehavior(@JsonProperty("componentName") String componentName, @JsonProperty("minimumVisibility") HueAppVisibility minimumVisibility) {
      this.componentName = componentName;
      this.minimumVisibility = minimumVisibility;
   }

   public String getComponentName() {
      return this.componentName;
   }

   public HueAppVisibility getMinimumVisibility() {
      return this.minimumVisibility;
   }

   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         ComponentBehavior that = (ComponentBehavior)object;
         return (new EqualsBuilder()).append(this.componentName, that.componentName).append(this.minimumVisibility, that.minimumVisibility).isEquals();
      } else {
         return false;
      }
   }

   public int hashCode() {
      return (new HashCodeBuilder(17, 37)).append(this.componentName).append(this.minimumVisibility).toHashCode();
   }

   public String toString() {
      return (new ToStringBuilder(this)).append("componentName", this.componentName).append("minimumVisibility", this.minimumVisibility).toString();
   }
}
