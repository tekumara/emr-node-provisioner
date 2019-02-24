package com.amazonaws.emr.node.provisioner.install.packaging;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public final class PackageResolver {
   private final Map<String, PackageGroup> groupMap;

   public PackageResolver(Iterable<PackageGroup> packageGroups) {
      this.groupMap = this.buildGroupMap(packageGroups);
   }

   public Set<String> resolve(String groupName) {
      PackageGroup packageGroup = (PackageGroup)this.groupMap.get(groupName);
      if (packageGroup == null) {
         throw new InvalidPackageGroupNameException("Invalid package group name: " + groupName);
      } else {
         return packageGroup.getPackages();
      }
   }

   public Set<String> resolve(List<String> groupNames) {
      Builder<String> builder = ImmutableSet.builder();
      Iterator var3 = groupNames.iterator();

      while(var3.hasNext()) {
         String bundleName = (String)var3.next();
         builder.addAll(this.resolve(bundleName));
      }

      return builder.build();
   }

   private Map<String, PackageGroup> buildGroupMap(Iterable<PackageGroup> groups) {
      return Maps.uniqueIndex(groups, new Function<PackageGroup, String>() {
         @Nullable
         public String apply(PackageGroup packageGroup) {
            return packageGroup.getGroupName();
         }
      });
   }
}
