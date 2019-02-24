package com.amazonaws.emr.node.provisioner.puppet.log.puppet.report.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(
   ignoreUnknown = true
)
public class UtilMetric {
   private String name;
   private String label;
   private List<List<String>> values;

   public UtilMetric() {
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getLabel() {
      return this.label;
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public List<List<String>> getValues() {
      return this.values;
   }

   public void setValues(List<List<String>> values) {
      this.values = values;
   }
}
