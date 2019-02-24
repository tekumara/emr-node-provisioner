package com.amazonaws.emr.node.provisioner.config.mapping;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;

public final class ConfigMappingTypeDeserializer extends JsonDeserializer<ConfigMappingType> {
   public ConfigMappingTypeDeserializer() {
   }

   public ConfigMappingType deserialize(JsonParser parser, DeserializationContext context) throws IOException {
      String value = parser.getValueAsString();
      if (value == null) {
         throw new JsonMappingException("Value cannot be retrieved as a string.");
      } else {
         return ConfigMappingType.fromName(value);
      }
   }
}
