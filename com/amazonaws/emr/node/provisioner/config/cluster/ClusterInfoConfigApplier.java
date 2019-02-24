package com.amazonaws.emr.node.provisioner.config.cluster;

import com.amazonaws.emr.node.provisioner.bigtop.config.PlatformContext;
import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.google.common.base.Preconditions;

public final class ClusterInfoConfigApplier implements ConfigApplier {
   private static final String CLUSTER_ID_KEY = "emr::cluster_id";
   private static final String INSTANCE_ID_KEY = "emr::instance_id";
   private static final String NODE_TYPE_KEY = "emr::node_type";
   private static final String MOUNTED_DIRS_KEY = "emr::mounted_dirs";
   public static final String APPS_MOUNTED_DIRS_KEY = "emr::apps_mounted_dirs";
   private final PlatformContext context;

   private ClusterInfoConfigApplier(ClusterInfoConfigApplier.Builder builder) {
      this.context = builder.context;
   }

   public void applyTo(Config config) throws ConfigException {
      config.put("emr::cluster_id", this.context.getClusterId());
      config.put("emr::instance_id", this.context.getInstanceId());
      config.put("emr::node_type", this.context.getNodeType());
      config.put((String)"emr::mounted_dirs", (Iterable)this.context.getMountedDirs());
      config.put((String)"emr::apps_mounted_dirs", (Iterable)this.context.getAppsMountedDirs());
   }

   public static ClusterInfoConfigApplier.Builder builder() {
      return new ClusterInfoConfigApplier.Builder();
   }

   public static final class Builder {
      private PlatformContext context;

      public Builder() {
      }

      public ClusterInfoConfigApplier.Builder withPlatformContext(PlatformContext context) {
         this.context = context;
         return this;
      }

      public ClusterInfoConfigApplier build() {
         Preconditions.checkNotNull(this.context, "PlatformContext is required");
         return new ClusterInfoConfigApplier(this);
      }
   }
}
