package com.amazonaws.emr.node.provisioner.secure.password.config.provider;

import com.amazonaws.emr.node.provisioner.secure.password.config.PasswordConfigKey;
import com.amazonaws.emr.node.provisioner.util.ResourceUtil;
import com.amazonaws.emr.node.provisioner.util.preconditions.CommonPreconditions;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.util.List;

public final class ResourcePasswordConfigKeyProvider implements PasswordConfigKeyProvider {
   private final String resourceName;

   private ResourcePasswordConfigKeyProvider(String resourceName) {
      this.resourceName = resourceName;
   }

   public static ResourcePasswordConfigKeyProvider using(String resourceName) {
      CommonPreconditions.require(resourceName, "resourceName");
      return new ResourcePasswordConfigKeyProvider(resourceName);
   }

   public List<PasswordConfigKey> provide() {
      ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
      String yaml = ResourceUtil.extractToString(this.resourceName);

      try {
         return (List)mapper.readValue(yaml, new TypeReference<List<PasswordConfigKey>>() {
         });
      } catch (IOException var4) {
         throw new RuntimeException(String.format("Unable to read PasswordConfigKeys from resource %s", this.resourceName), var4);
      }
   }
}
