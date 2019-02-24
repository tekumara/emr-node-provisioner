package com.amazonaws.emr.node.provisioner.bigtop.config.matcher;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.util.Set;

public final class ComponentMatcherDeserializer extends JsonDeserializer<ComponentMatcher> {
   private static final String ALL_MATCH_TYPE = "all";
   private static final String ANY_MATCH_TYPE = "any";

   public ComponentMatcherDeserializer() {
   }

   public ComponentMatcher deserialize(JsonParser parser, DeserializationContext context) throws IOException {
      ComponentMatcherDeserializer.ComponentMatcherDescription description = (ComponentMatcherDeserializer.ComponentMatcherDescription)parser.readValueAs(ComponentMatcherDeserializer.ComponentMatcherDescription.class);
      String matchType = description.getMatchType();
      if (matchType.equals("all")) {
         return AllComponentMatcher.of(description.getComponentNames());
      } else if (matchType.equals("any")) {
         return AnyComponentMatcher.of(description.getComponentNames());
      } else {
         throw new JsonMappingException(String.format("Unknown match type %s", matchType));
      }
   }

   private static final class ComponentMatcherDescription {
      private final Set<String> componentNames;
      private final String matchType;

      @JsonCreator
      public ComponentMatcherDescription(@JsonProperty("componentNames") Set<String> componentNames, @JsonProperty("matchType") String matchType) {
         this.componentNames = componentNames;
         this.matchType = matchType;
      }

      public Set<String> getComponentNames() {
         return this.componentNames;
      }

      public String getMatchType() {
         return this.matchType;
      }
   }
}
