package com.amazonaws.emr.node.provisioner.config.hdfs;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.google.common.base.Preconditions;
import java.util.Iterator;

public final class HdfsConfigApplier implements ConfigApplier {
   private static final String ENCRYPTION_ZONES_CLASSIFICATION = "hdfs-encryption-zones";
   private static final String DIRS_CONFIG_KEY = "hadoop::init_hdfs::dirs";
   private static final String ENCRYPTION_KEY_PROPERTY = "key";
   private final Config appConfig;

   private HdfsConfigApplier(HdfsConfigApplier.Builder builder) {
      this.appConfig = builder.appConfig;
   }

   public void applyTo(Config config) throws ConfigException {
      Config dirsConfig = config.resolveConfig("hadoop::init_hdfs::dirs");
      if (this.appConfig.hasKey("hdfs-encryption-zones")) {
         Config zonesConfig = this.appConfig.getConfig("hdfs-encryption-zones");
         Iterator var4 = zonesConfig.keys().iterator();

         while(var4.hasNext()) {
            String hdfsPath = (String)var4.next();
            String encryptionKeyName = zonesConfig.getString(hdfsPath);
            Config dirConfig = dirsConfig.resolveConfig(hdfsPath);
            dirConfig.put("key", encryptionKeyName);
         }
      }

   }

   public static HdfsConfigApplier.Builder builder() {
      return new HdfsConfigApplier.Builder();
   }

   public static final class Builder {
      private Config appConfig;

      public Builder() {
      }

      public HdfsConfigApplier.Builder withAppConfig(Config appConfig) {
         this.appConfig = appConfig;
         return this;
      }

      public HdfsConfigApplier build() {
         Preconditions.checkNotNull(this.appConfig, "AppConfig is required");
         return new HdfsConfigApplier(this);
      }
   }
}
