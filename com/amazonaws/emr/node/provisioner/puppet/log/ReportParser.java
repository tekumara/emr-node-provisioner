package com.amazonaws.emr.node.provisioner.puppet.log;

import com.amazonaws.emr.node.provisioner.puppet.log.puppet.report.model.Report;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;

public class ReportParser {
   private ReportParser() {
   }

   public static Report parse(String file) throws IOException {
      ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
      Report puppetReport = (Report)mapper.readValue(file, Report.class);
      return puppetReport;
   }
}
