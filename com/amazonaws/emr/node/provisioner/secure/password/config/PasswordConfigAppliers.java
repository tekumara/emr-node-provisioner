package com.amazonaws.emr.node.provisioner.secure.password.config;

import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import com.amazonaws.emr.node.provisioner.secure.password.config.provider.ResourcePasswordConfigKeyProvider;
import com.amazonaws.emr.node.provisioner.secure.password.generate.MysqlPasswordGenerator;
import com.amazonaws.emr.node.provisioner.secure.password.hash.MysqlPasswordHasher;
import java.util.List;
import java.util.Random;

public final class PasswordConfigAppliers {
   private static final String DEFAULT_KEYS_RESOURCE = "/secure/generated-password-keys.yaml";

   private PasswordConfigAppliers() {
   }

   public static ConfigApplier defaultApplier(Random random) {
      List<PasswordConfigKey> keys = ResourcePasswordConfigKeyProvider.using("/secure/generated-password-keys.yaml").provide();
      return mysqlApplier(random, keys);
   }

   private static ConfigApplier mysqlApplier(Random random, List<PasswordConfigKey> passwordConfigKeys) {
      return PasswordConfigApplier.builder().withPasswordGenerator(MysqlPasswordGenerator.newInstance(random)).withPasswordHasher(MysqlPasswordHasher.newInstance()).withPasswordConfigKeys(passwordConfigKeys).build();
   }
}
