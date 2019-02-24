package com.amazonaws.emr.node.provisioner.secure.password.config;

import com.amazonaws.emr.node.provisioner.util.preconditions.CommonPreconditions;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class PasswordConfigKey {
   private final String passwordKey;
   private final String passwordHashKey;

   private PasswordConfigKey(String passwordKey, String passwordHashKey) {
      this.passwordKey = passwordKey;
      this.passwordHashKey = passwordHashKey;
   }

   @JsonCreator
   public static PasswordConfigKey of(@JsonProperty("passwordKey") String passwordKey, @JsonProperty("passwordHashKey") String passwordHashKey) {
      CommonPreconditions.require(passwordKey, "passwordKey");
      CommonPreconditions.require(passwordHashKey, "passwordHashKey");
      return new PasswordConfigKey(passwordKey, passwordHashKey);
   }

   public String getPasswordKey() {
      return this.passwordKey;
   }

   public String getPasswordHashKey() {
      return this.passwordHashKey;
   }
}
