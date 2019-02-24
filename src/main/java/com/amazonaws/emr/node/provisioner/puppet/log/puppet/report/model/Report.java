package com.amazonaws.emr.node.provisioner.puppet.log.puppet.report.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(
   ignoreUnknown = true
)
public class Report {
   @JsonProperty("metrics")
   private Metrics puppetMetrics;
   @JsonProperty("host")
   String host;
   @JsonProperty("time")
   String time;
   @JsonProperty("kind")
   String kind;
   @JsonProperty("report_format")
   String reportFormat;
   @JsonProperty("puppet_version")
   String puppetVersion;
   @JsonProperty("configuration_version")
   String configurationVersion;
   @JsonProperty("transaction_uuid")
   String transactionUuid;
   @JsonProperty("environment")
   String environment;
   @JsonProperty("status")
   String status;

   public Report() {
   }

   public Metrics getPuppetMetrics() {
      return this.puppetMetrics;
   }

   public void setPuppetMetrics(Metrics puppetMetrics) {
      this.puppetMetrics = puppetMetrics;
   }

   public String getHost() {
      return this.host;
   }

   public void setHost(String host) {
      this.host = host;
   }

   public String getTime() {
      return this.time;
   }

   public void setTime(String time) {
      this.time = time;
   }

   public String getKind() {
      return this.kind;
   }

   public void setKind(String kind) {
      this.kind = kind;
   }

   public String getReportFormat() {
      return this.reportFormat;
   }

   public void setReportFormat(String reportFormat) {
      this.reportFormat = reportFormat;
   }

   public String getPuppetVersion() {
      return this.puppetVersion;
   }

   public void setPuppetVersion(String puppetVersion) {
      this.puppetVersion = puppetVersion;
   }

   public String getConfigurationVersion() {
      return this.configurationVersion;
   }

   public void setConfigurationVersion(String configurationVersion) {
      this.configurationVersion = configurationVersion;
   }

   public String getTransactionUuid() {
      return this.transactionUuid;
   }

   public void setTransactionUuid(String transactionUuid) {
      this.transactionUuid = transactionUuid;
   }

   public String getEnvironment() {
      return this.environment;
   }

   public void setEnvironment(String environment) {
      this.environment = environment;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }
}
