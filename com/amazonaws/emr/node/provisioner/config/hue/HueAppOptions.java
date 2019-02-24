package com.amazonaws.emr.node.provisioner.config.hue;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class HueAppOptions {
   private final Set<String> appsToBlacklist;
   private final Set<String> appsToInstall;

   private HueAppOptions(Set<String> appsToBlacklist, Set<String> appsToInstall) {
      this.appsToBlacklist = ImmutableSet.copyOf(appsToBlacklist);
      this.appsToInstall = ImmutableSet.copyOf(appsToInstall);
   }

   public static HueAppOptions newInstance(Set<String> appsToBlacklist, Set<String> appsToInstall) {
      return new HueAppOptions(appsToBlacklist, appsToInstall);
   }

   public Set<String> getAppsToBlacklist() {
      return this.appsToBlacklist;
   }

   public Set<String> getAppsToInstall() {
      return this.appsToInstall;
   }

   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         HueAppOptions that = (HueAppOptions)object;
         return (new EqualsBuilder()).append(this.appsToBlacklist, that.appsToBlacklist).append(this.appsToInstall, that.appsToInstall).isEquals();
      } else {
         return false;
      }
   }

   public int hashCode() {
      return (new HashCodeBuilder(17, 37)).append(this.appsToBlacklist).append(this.appsToInstall).toHashCode();
   }

   public String toString() {
      return (new ToStringBuilder(this)).append("appsToBlacklist", this.appsToBlacklist).append("appsToInstall", this.appsToInstall).toString();
   }
}
