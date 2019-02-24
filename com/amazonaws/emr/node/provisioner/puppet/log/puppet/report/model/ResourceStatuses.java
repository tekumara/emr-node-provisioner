package com.amazonaws.emr.node.provisioner.puppet.log.puppet.report.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(
   ignoreUnknown = true
)
public class ResourceStatuses {
   @JsonProperty("Schedule[daily]")
   private ResourceStatus schedule;

   public ResourceStatuses() {
   }

   public ResourceStatus getSchedule() {
      return this.schedule;
   }

   public void setSchedule(ResourceStatus schedule) {
      this.schedule = schedule;
   }
}
