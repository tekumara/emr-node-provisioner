package com.amazonaws.emr.node.provisioner.puppet.log.puppet.report.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(
   ignoreUnknown = true
)
public class Metrics {
   @JsonInclude(Include.NON_EMPTY)
   private UtilMetric resources;
   @JsonInclude(Include.NON_EMPTY)
   private UtilMetric time;
   @JsonInclude(Include.NON_EMPTY)
   private UtilMetric changes;
   @JsonInclude(Include.NON_EMPTY)
   private UtilMetric events;

   public Metrics() {
   }

   public UtilMetric getResources() {
      return this.resources;
   }

   public void setResources(UtilMetric resources) {
      this.resources = resources;
   }

   public UtilMetric getTime() {
      return this.time;
   }

   public void setTime(UtilMetric time) {
      this.time = time;
   }

   public UtilMetric getChanges() {
      return this.changes;
   }

   public void setChanges(UtilMetric changes) {
      this.changes = changes;
   }

   public UtilMetric getEvents() {
      return this.events;
   }

   public void setEvents(UtilMetric events) {
      this.events = events;
   }
}
