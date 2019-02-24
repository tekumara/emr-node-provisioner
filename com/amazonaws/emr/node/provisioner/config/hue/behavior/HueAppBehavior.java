package com.amazonaws.emr.node.provisioner.config.hue.behavior;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.List;

@JsonDeserialize(
   builder = HueAppBehavior.Builder.class
)
public final class HueAppBehavior {
   private final String appName;
   private final boolean hasSeparatePackage;
   private final HueAppVisibility defaultVisibility;
   private final List<ComponentBehavior> componentBehaviors;

   private HueAppBehavior(HueAppBehavior.Builder builder) {
      this.appName = builder.appName;
      this.hasSeparatePackage = builder.hasSeparatePackage;
      this.defaultVisibility = builder.defaultVisibility;
      this.componentBehaviors = ImmutableList.copyOf(builder.componentBehaviors);
   }

   public static HueAppBehavior.Builder builder() {
      return new HueAppBehavior.Builder();
   }

   public String getAppName() {
      return this.appName;
   }

   public boolean hasSeparatePackage() {
      return this.hasSeparatePackage;
   }

   public HueAppVisibility getDefaultVisibility() {
      return this.defaultVisibility;
   }

   public List<ComponentBehavior> getComponentBehaviors() {
      return this.componentBehaviors;
   }

   @JsonPOJOBuilder
   public static final class Builder {
      private String appName;
      private Boolean hasSeparatePackage;
      private HueAppVisibility defaultVisibility;
      private List<ComponentBehavior> componentBehaviors;

      private Builder() {
         this.componentBehaviors = ImmutableList.of();
      }

      public HueAppBehavior.Builder withAppName(String appName) {
         this.appName = appName;
         return this;
      }

      public HueAppBehavior.Builder withHasSeparatePackage(boolean hasSeparatePackage) {
         this.hasSeparatePackage = hasSeparatePackage;
         return this;
      }

      public HueAppBehavior.Builder withDefaultVisibility(HueAppVisibility defaultVisibility) {
         this.defaultVisibility = defaultVisibility;
         return this;
      }

      public HueAppBehavior.Builder withComponentBehaviors(List<ComponentBehavior> componentBehaviors) {
         this.componentBehaviors = componentBehaviors;
         return this;
      }

      public HueAppBehavior build() {
         Preconditions.checkNotNull(this.appName, "AppName is required");
         Preconditions.checkNotNull(this.hasSeparatePackage, "HasSeparatePackage is required");
         Preconditions.checkNotNull(this.defaultVisibility, "DefaultVisibility is required");
         return new HueAppBehavior(this);
      }
   }
}
