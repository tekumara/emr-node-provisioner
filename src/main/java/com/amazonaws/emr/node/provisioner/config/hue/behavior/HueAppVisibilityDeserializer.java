package com.amazonaws.emr.node.provisioner.config.hue.behavior;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;

public final class HueAppVisibilityDeserializer extends JsonDeserializer<HueAppVisibility> {
   public HueAppVisibilityDeserializer() {
   }

   public HueAppVisibility deserialize(JsonParser parser, DeserializationContext context) throws IOException {
      String value = parser.getValueAsString();
      if (value == null) {
         throw new JsonMappingException("Value cannot be retrieved as a string.");
      } else {
         return HueAppVisibility.fromName(value);
      }
   }
}
