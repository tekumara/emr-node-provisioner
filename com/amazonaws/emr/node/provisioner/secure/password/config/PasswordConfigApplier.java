package com.amazonaws.emr.node.provisioner.secure.password.config;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.secure.password.generate.PasswordGenerator;
import com.amazonaws.emr.node.provisioner.secure.password.hash.PasswordHasher;
import com.amazonaws.emr.node.provisioner.util.preconditions.CommonPreconditions;
import java.util.Iterator;
import java.util.List;

public final class PasswordConfigApplier implements ConfigApplier {
   private final PasswordGenerator passwordGenerator;
   private final PasswordHasher passwordHasher;
   private final List<PasswordConfigKey> passwordConfigKeys;

   private PasswordConfigApplier(PasswordConfigApplier.Builder builder) {
      this.passwordGenerator = builder.passwordGenerator;
      this.passwordHasher = builder.passwordHasher;
      this.passwordConfigKeys = builder.passwordConfigKeys;
   }

   public static PasswordConfigApplier.Builder builder() {
      return new PasswordConfigApplier.Builder();
   }

   public void applyTo(Config config) throws ConfigException {
      CommonPreconditions.require(config, "config");
      Iterator var2 = this.passwordConfigKeys.iterator();

      while(var2.hasNext()) {
         PasswordConfigKey passwordConfigKey = (PasswordConfigKey)var2.next();
         String password = this.passwordGenerator.generate();
         config.put(passwordConfigKey.getPasswordKey(), password);
         String passwordHash = this.passwordHasher.hash(password);
         config.put(passwordConfigKey.getPasswordHashKey(), passwordHash);
      }

   }

   public static final class Builder {
      private PasswordGenerator passwordGenerator;
      private PasswordHasher passwordHasher;
      private List<PasswordConfigKey> passwordConfigKeys;

      private Builder() {
      }

      public PasswordConfigApplier.Builder withPasswordGenerator(PasswordGenerator passwordGenerator) {
         this.passwordGenerator = passwordGenerator;
         return this;
      }

      public PasswordConfigApplier.Builder withPasswordHasher(PasswordHasher passwordHasher) {
         this.passwordHasher = passwordHasher;
         return this;
      }

      public PasswordConfigApplier.Builder withPasswordConfigKeys(List<PasswordConfigKey> passwordConfigKeys) {
         this.passwordConfigKeys = passwordConfigKeys;
         return this;
      }

      public PasswordConfigApplier build() {
         CommonPreconditions.require(this.passwordGenerator, "passwordGenerator");
         CommonPreconditions.require(this.passwordHasher, "passwordHasher");
         CommonPreconditions.require(this.passwordConfigKeys, "passwordConfigKeys");
         return new PasswordConfigApplier(this);
      }
   }
}
