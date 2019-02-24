package com.amazonaws.emr.node.provisioner.platform.model;

import com.google.api.client.util.Key;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class Configuration {
   @Key
   private String classification;
   @Key
   private Map<String, String> properties;
   @Key
   private List<Configuration> configurations;

   public Configuration() {
   }

   public String getClassification() {
      return this.classification;
   }

   public Map<String, String> getProperties() {
      return this.properties;
   }

   public List<Configuration> getConfigurations() {
      return this.configurations;
   }

   public static final class Builder {
      private String classification;
      private Map<String, String> properties = Collections.emptyMap();
      private List<Configuration> configurations = Collections.emptyList();

      public Builder() {
      }

      public Configuration.Builder withProperties(Map<String, String> properties) {
         this.properties = properties;
         return this;
      }

      public Configuration.Builder withClassification(String classification) {
         this.classification = classification;
         return this;
      }

      public Configuration.Builder withClassification(List<Configuration> configurations) {
         this.configurations = configurations;
         return this;
      }

      public Configuration build() {
         Configuration configuration = new Configuration();
         configuration.classification = this.classification;
         configuration.properties = this.properties;
         configuration.configurations = this.configurations;
         return configuration;
      }
   }
}
