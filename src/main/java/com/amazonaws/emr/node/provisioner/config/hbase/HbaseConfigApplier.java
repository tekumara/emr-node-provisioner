package com.amazonaws.emr.node.provisioner.config.hbase;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.config.ConfigFactory;
import com.amazonaws.emr.node.provisioner.config.ConfigPath;
import com.amazonaws.emr.node.provisioner.util.ResourceUtil;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import java.io.IOException;
import javax.annotation.Nullable;

public class HbaseConfigApplier implements ConfigApplier {
   static final String HBASE_OVERRIDES_KEY = "hadoop_hbase::common_config::hbase_overrides";
   static final String HBASE_SITE_OVERRIDES_KEY = "hadoop_hbase::common_config::hbase_site_overrides";
   static final String HBASE_EMR_STORAGEMODE = "hbase.emr.storageMode";
   static final String HBASE_EMR_READ_REPLICA = "hbase.emr.readreplica.enabled";
   static final String ON_S3_CONFIG_KEY = "hadoop_hbase::common_config::on_s3";
   static final String HBASE_BUCKETCACHE_IOENGINE = "hbase.bucketcache.ioengine";
   static final String HBASE_ON_S3_DEFAULTS = "/config/applications/hbase/hbase-on-s3-default-config.yaml";
   static final String HBASE_META_SUFFIX = "hbase.meta.table.suffix";
   static final String HBASE_GLOBAL_READONLY = "hbase.global.readonly.enabled";
   static final String HBASE_META_STARTUP_REFRESH = "hbase.meta.startup.refresh";
   private final Config appConfig;
   private final String clusterId;

   private HbaseConfigApplier(HbaseConfigApplier.Builder builder) {
      this.appConfig = builder.appConfig;
      this.clusterId = builder.clusterId;
   }

   public void applyTo(Config config) throws ConfigException {
      if (isHBaseONS3Cluster(config)) {
         config.put("hadoop_hbase::common_config::on_s3", true);
         config.merge(buildHBaseOnS3Config(config));
         if (isHBaseReadReplicaCluster(config)) {
            config.merge(buildReadReplicaConfig(this.clusterId));
         }
      } else {
         config.put("hadoop_hbase::common_config::on_s3", false);
      }

   }

   @VisibleForTesting
   static Config buildHBaseOnS3Config(Config config) throws ConfigException {
      try {
         Config defaults = ConfigFactory.createFromYaml(ResourceUtil.extractToString("/config/applications/hbase/hbase-on-s3-default-config.yaml"));
         Config overrides = defaults.resolveConfig("hadoop_hbase::common_config::hbase_site_overrides");
         overrides.merge(buildBucketCacheConfig(config));
         return defaults;
      } catch (IOException var3) {
         throw new RuntimeException(String.format("Unable to read HBase on S3 defaults from %s", "/config/applications/hbase/hbase-on-s3-default-config.yaml"), var3);
      }
   }

   @VisibleForTesting
   static Config buildBucketCacheConfig(Config config) throws ConfigException {
      Config bucketCacheConfig = Config.empty();
      Iterable<String> appsMountedDirs = config.getStringList("emr::apps_mounted_dirs");
      if (Iterables.size(appsMountedDirs) <= 0) {
         throw new ConfigException("Expected there to be at least one entry for emr::apps_mounted_dirs");
      } else {
         Iterable<String> bucketCacheFiles = Iterables.transform(appsMountedDirs, new Function<String, String>() {
            @Nullable
            public String apply(@Nullable String input) {
               return input + "/hbase/bucketcache";
            }
         });
         bucketCacheConfig.put("hbase.bucketcache.ioengine", "files:" + Joiner.on(",").join(bucketCacheFiles));
         return bucketCacheConfig;
      }
   }

   @VisibleForTesting
   static Config buildReadReplicaConfig(String suffix) throws ConfigException {
      Config config = Config.empty();
      Config readReplicaConfig = config.resolveConfig("hadoop_hbase::common_config::hbase_site_overrides");
      readReplicaConfig.put("hbase.meta.table.suffix", suffix);
      readReplicaConfig.put("hbase.global.readonly.enabled", "true");
      readReplicaConfig.put("hbase.meta.startup.refresh", "true");
      return config;
   }

   private static boolean isHBaseONS3Cluster(Config config) throws ConfigException {
      ConfigPath configPath = new ConfigPath("hadoop_hbase::common_config::hbase_overrides");
      if (config.hasKey(configPath)) {
         Config overrides = config.getConfig(configPath);
         if (overrides.hasKey("hbase.emr.storageMode")) {
            String storageMode = overrides.getString("hbase.emr.storageMode");
            return storageMode.toUpperCase().equals(HbaseConfigApplier.StorageMode.S3.name());
         }
      }

      return false;
   }

   private static boolean isHBaseReadReplicaCluster(Config config) throws ConfigException {
      ConfigPath configPath = new ConfigPath("hadoop_hbase::common_config::hbase_overrides");
      if (config.hasKey(configPath)) {
         Config overrides = config.getConfig(configPath);
         if (overrides.hasKey("hbase.emr.readreplica.enabled")) {
            return overrides.getBoolean("hbase.emr.readreplica.enabled");
         }
      }

      return false;
   }

   public static HbaseConfigApplier.Builder builder() {
      return new HbaseConfigApplier.Builder();
   }

   public static final class Builder {
      private Config appConfig;
      private String clusterId;

      public Builder() {
      }

      public HbaseConfigApplier.Builder withAppConfig(Config appConfig) {
         this.appConfig = appConfig;
         return this;
      }

      public HbaseConfigApplier.Builder withClusterId(String clusterId) {
         this.clusterId = clusterId;
         return this;
      }

      public HbaseConfigApplier build() {
         Preconditions.checkNotNull(this.appConfig, "AppConfig is required");
         Preconditions.checkNotNull(this.clusterId, "ClusterId is required");
         return new HbaseConfigApplier(this);
      }
   }

   private static enum StorageMode {
      S3,
      HDFS;

      private StorageMode() {
      }
   }
}
