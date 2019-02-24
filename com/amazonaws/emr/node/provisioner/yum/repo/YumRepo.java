package com.amazonaws.emr.node.provisioner.yum.repo;

import com.google.api.client.util.Preconditions;

public final class YumRepo {
   private final String id;
   private final String baseUrl;
   private final String name;
   private final Boolean enabled;
   private final Boolean gpgCheck;

   private YumRepo(YumRepo.Builder builder) {
      this.id = builder.id;
      this.baseUrl = builder.baseUrl;
      this.name = builder.name;
      this.enabled = builder.enabled;
      this.gpgCheck = builder.gpgCheck;
   }

   public String getId() {
      return this.id;
   }

   public String getBaseUrl() {
      return this.baseUrl;
   }

   public boolean hasName() {
      return this.name != null;
   }

   public String getName() {
      return this.name;
   }

   public boolean hasEnabled() {
      return this.enabled != null;
   }

   public Boolean getEnabled() {
      return this.enabled;
   }

   public boolean hasGpgCheck() {
      return this.gpgCheck != null;
   }

   public Boolean getGpgCheck() {
      return this.gpgCheck;
   }

   public static YumRepo.Builder builder() {
      return new YumRepo.Builder();
   }

   public static final class Builder {
      private String id;
      private String baseUrl;
      private String name;
      private Boolean enabled;
      private Boolean gpgCheck;

      public Builder() {
      }

      public YumRepo.Builder withId(String id) {
         this.id = id;
         return this;
      }

      public YumRepo.Builder withBaseUrl(String baseUrl) {
         this.baseUrl = baseUrl;
         return this;
      }

      public YumRepo.Builder withName(String description) {
         this.name = description;
         return this;
      }

      public YumRepo.Builder withEnabled(Boolean enabled) {
         this.enabled = enabled;
         return this;
      }

      public YumRepo.Builder withGpgCheck(Boolean gpgCheck) {
         this.gpgCheck = gpgCheck;
         return this;
      }

      public YumRepo build() {
         this.checkNotEmpty("Id", this.id);
         this.checkNotEmpty("Base url", this.baseUrl);
         return new YumRepo(this);
      }

      private void checkNotEmpty(String argumentName, String argumentValue) {
         Preconditions.checkNotNull(argumentValue, "%s is required", new Object[]{argumentName});
         Preconditions.checkArgument(!argumentValue.isEmpty(), "%s must not be empty", new Object[]{argumentName});
      }
   }
}
