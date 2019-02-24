package com.amazonaws.emr.node.provisioner.config.mapping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Collection;

public final class ConfigMappingGroup {
   private final ConfigMappingType mappingType;
   private final Collection<ConfigMapping> mappings;

   @JsonCreator
   public ConfigMappingGroup(@JsonProperty("mappingType") @JsonDeserialize(using = ConfigMappingTypeDeserializer.class) ConfigMappingType mappingType, @JsonProperty("mappings") Collection<ConfigMapping> mappings) {
      this.mappingType = mappingType;
      this.mappings = mappings;
   }

   public ConfigMappingType getMappingType() {
      return this.mappingType;
   }

   public Collection<ConfigMapping> getMappings() {
      return this.mappings;
   }
}
