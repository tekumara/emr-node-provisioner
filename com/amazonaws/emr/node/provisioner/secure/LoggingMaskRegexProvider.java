package com.amazonaws.emr.node.provisioner.secure;

import com.amazonaws.emr.node.provisioner.util.ResourceUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.util.List;

public final class LoggingMaskRegexProvider {
   private static final String DEFAULT_LOGGING_MASKS_RESOURCE = "/secure/logging-masks.yaml";

   private LoggingMaskRegexProvider() {
   }

   public static LoggingMaskRegexProvider newInstance() {
      return new LoggingMaskRegexProvider();
   }

   public List<String> provideDefault() {
      return this.provide("/secure/logging-masks.yaml");
   }

   public List<String> provide(String resourcePath) {
      ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
      String yaml = ResourceUtil.extractToString(resourcePath);

      try {
         return (List)mapper.readValue(yaml, new TypeReference<List<String>>() {
         });
      } catch (IOException var5) {
         throw new RuntimeException(var5);
      }
   }
}
