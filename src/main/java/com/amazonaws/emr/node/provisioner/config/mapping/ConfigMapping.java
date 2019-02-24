package com.amazonaws.emr.node.provisioner.config.mapping;

import com.amazonaws.emr.node.provisioner.config.ConfigPath;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class ConfigMapping {
   private final ConfigPath sourcePath;
   private final ConfigPath destinationPath;

   @JsonCreator
   public ConfigMapping(@JsonProperty("sourcePath") ConfigPath sourcePath, @JsonProperty("destinationPath") ConfigPath destinationPath) {
      this.sourcePath = sourcePath;
      this.destinationPath = destinationPath;
   }

   public ConfigPath getSourcePath() {
      return this.sourcePath;
   }

   public ConfigPath getDestinationPath() {
      return this.destinationPath;
   }

   public String toString() {
      return (new ToStringBuilder(this)).append("sourcePath", this.sourcePath).append("destinationPath", this.destinationPath).toString();
   }
}
