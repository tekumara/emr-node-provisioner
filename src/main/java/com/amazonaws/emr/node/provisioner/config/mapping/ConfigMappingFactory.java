package com.amazonaws.emr.node.provisioner.config.mapping;

import com.amazonaws.emr.node.provisioner.util.ResourceUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.util.List;

public final class ConfigMappingFactory {
   private ConfigMappingFactory() {
   }

   public static List<ConfigMapping> createManyFromYaml(String yaml) throws IOException {
      ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
      return (List)mapper.readValue(yaml, new TypeReference<List<ConfigMapping>>() {
      });
   }

   public static List<ConfigMapping> createManyFromYamlResource(String resourceName) {
      try {
         return createManyFromYaml(ResourceUtil.extractToString(resourceName));
      } catch (IOException var2) {
         throw new RuntimeException(String.format("Failed to extract resource %s to ConfigMappings", resourceName), var2);
      }
   }
}
