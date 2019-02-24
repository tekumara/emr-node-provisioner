package com.amazonaws.emr.node.provisioner.puppet.log.puppet.report.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(
   ignoreUnknown = true
)
public class ResourceStatus {
   @JsonProperty("resource")
   String resource;
   @JsonProperty("file")
   String file;
   @JsonProperty("line")
   int line;
   @JsonProperty("evaluation_time")
   double evaluationTime;
   @JsonProperty("change_count")
   int changeCount;
   @JsonProperty("out_of_sync_count")
   int outOfSyncCount;
   @JsonProperty("tags")
   List<String> tags;
   @JsonProperty("time")
   String time;
   @JsonProperty("out_of_sync")
   Boolean outOfSync;
   @JsonProperty("changed")
   Boolean changed;
   @JsonProperty("resource_type")
   String resourceType;
   @JsonProperty("title")
   String title;
   @JsonProperty("skipped")
   Boolean skipped;
   @JsonProperty("failed")
   Boolean failed;
   @JsonProperty("containment_path")
   List<String> containmentPath;

   public ResourceStatus() {
   }

   public String getResource() {
      return this.resource;
   }

   public void setResource(String resource) {
      this.resource = resource;
   }

   public String getFile() {
      return this.file;
   }

   public void setFile(String file) {
      this.file = file;
   }

   public int getLine() {
      return this.line;
   }

   public void setLine(int line) {
      this.line = line;
   }

   public double getEvaluationTime() {
      return this.evaluationTime;
   }

   public void setEvaluationTime(double evaluationTime) {
      this.evaluationTime = evaluationTime;
   }

   public int getChangeCount() {
      return this.changeCount;
   }

   public void setChangeCount(int changeCount) {
      this.changeCount = changeCount;
   }

   public int getOutOfSyncCount() {
      return this.outOfSyncCount;
   }

   public void setOutOfSyncCount(int outOfSyncCount) {
      this.outOfSyncCount = outOfSyncCount;
   }

   public List<String> getTags() {
      return this.tags;
   }

   public void setTags(List<String> tags) {
      this.tags = tags;
   }

   public String getTime() {
      return this.time;
   }

   public void setTime(String time) {
      this.time = time;
   }

   public Boolean getOutOfSync() {
      return this.outOfSync;
   }

   public void setOutOfSync(Boolean outOfSync) {
      this.outOfSync = outOfSync;
   }

   public Boolean getChanged() {
      return this.changed;
   }

   public void setChanged(Boolean changed) {
      this.changed = changed;
   }

   public String getResourceType() {
      return this.resourceType;
   }

   public void setResourceType(String resourceType) {
      this.resourceType = resourceType;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public Boolean getSkipped() {
      return this.skipped;
   }

   public void setSkipped(Boolean skipped) {
      this.skipped = skipped;
   }

   public Boolean getFailed() {
      return this.failed;
   }

   public void setFailed(Boolean failed) {
      this.failed = failed;
   }

   public List<String> getContainmentPath() {
      return this.containmentPath;
   }

   public void setContainmentPath(List<String> containmentPath) {
      this.containmentPath = containmentPath;
   }
}
