package com.amazonaws.emr.node.provisioner.bigtop.config;

import com.amazonaws.emr.node.provisioner.config.jobflow.DefaultJobFlowInfoProvider;
import com.amazonaws.emr.node.provisioner.config.jobflow.JobFlowInfoProvider;
import com.amazonaws.emr.node.provisioner.json.JsonKeyValueMaskerFactory;
import com.amazonaws.emr.node.provisioner.platform.EmrPlatform;
import com.amazonaws.emr.node.provisioner.platform.EmrPlatformClient;
import com.amazonaws.emr.node.provisioner.platform.model.GetConfigurationResponse;
import com.amazonaws.emr.node.provisioner.util.JsonUtil;
import com.amazonaws.emr.node.provisioner.util.StringUtil;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PlatformContextProvider {
   private static final Logger logger = LoggerFactory.getLogger(PlatformConfigApplierFactory.class);
   private final EmrPlatform platform;
   private final JobFlowInfoProvider jobFlowInfoProvider;

   public PlatformContextProvider() {
      this(new EmrPlatformClient(), new DefaultJobFlowInfoProvider());
   }

   public PlatformContextProvider(EmrPlatform platform, JobFlowInfoProvider jobFlowInfoProvider) {
      this.platform = platform;
      this.jobFlowInfoProvider = jobFlowInfoProvider;
   }

   public PlatformContext provide() throws IOException {
      GetConfigurationResponse response = this.platform.getConfiguration();
      this.printConfiguration(response);
      return PlatformContext.builder().withClusterId(response.getClusterId()).withInstanceId(response.getInstanceId()).withComponentNames(response.getComponentNames()).withConfigurations(response.getConfigurations()).withNodeType(response.getNodeType()).withJobFlowInfo(this.jobFlowInfoProvider.provide()).withMountedDirs(response.getMountedDirs()).build();
   }

   private void printConfiguration(GetConfigurationResponse response) throws IOException {
      GetConfigurationResponse maskedResponse = (GetConfigurationResponse)JsonKeyValueMaskerFactory.createDefault().mask(response, GetConfigurationResponse.class);
      String indentedJson = StringUtil.indent(JsonUtil.prettyDump((Object)maskedResponse));
      logger.info("platform configuration response:\n{}", indentedJson);
   }
}
