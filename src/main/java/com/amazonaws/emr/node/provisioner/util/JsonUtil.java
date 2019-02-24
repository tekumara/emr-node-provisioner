package com.amazonaws.emr.node.provisioner.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public final class JsonUtil {
   private static final ObjectMapper mapper = new ObjectMapper();

   private JsonUtil() {
   }

   public static String dump(Object value) throws IOException {
      return mapper.writeValueAsString(value);
   }

   public static String prettyDump(JsonNode node) throws IOException {
      return prettyDump(mapper.treeToValue(node, Object.class));
   }

   public static String prettyDump(Object value) throws IOException {
      return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
   }

   static {
      mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
   }
}
