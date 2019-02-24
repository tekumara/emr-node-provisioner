package com.amazonaws.emr.node.provisioner.config.jobflow;

import com.amazonaws.emr.node.provisioner.pojo.InstanceType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class JobFlowInfoFactory {
   private static final ObjectMapper objectMapper = new ObjectMapper();
   private static final Logger logger = LoggerFactory.getLogger(JobFlowInfoFactory.class);
   private static final String INSTANCE_TYPE_FIELD_SUFFIX = "InstanceType";
   private static final String INSTANCE_COUNT_FIELD = "instanceCount";
   private static final InstanceType DEFAULT_INSTANCE_TYPE = InstanceTypes.getInstanceType("m3.xlarge");
   private static final int DEFAULT_INSTANCE_COUNT = 1;

   private JobFlowInfoFactory() {
   }

   static JobFlowInfo getJobFlowInfo(InputStream is) throws IOException {
      JsonNode root = objectMapper.readTree(is);
      int instanceCount = getInstanceCount(root);
      InstanceType masterInstanceType = getInstanceType(root, JobFlowInfoFactory.InstanceGroupType.MASTER);
      if (instanceCount == 1) {
         return JobFlowInfos.singleNodeCluster(masterInstanceType);
      } else {
         InstanceType coreInstanceType = getInstanceType(root, JobFlowInfoFactory.InstanceGroupType.SLAVE);
         return JobFlowInfos.multiNodeCluster(masterInstanceType, coreInstanceType, instanceCount);
      }
   }

   private static InstanceType getInstanceType(JsonNode root, JobFlowInfoFactory.InstanceGroupType instanceGroupType) {
      String instanceTypeField = instanceGroupType + "InstanceType";
      if (root.has(instanceTypeField)) {
         String instanceType = root.get(instanceTypeField).asText();

         try {
            return InstanceTypes.getInstanceType(instanceType);
         } catch (IllegalArgumentException var5) {
            logger.warn("Returning default ({}) for unknown {} instance type '{}'", new Object[]{DEFAULT_INSTANCE_TYPE, instanceGroupType, instanceType});
            return DEFAULT_INSTANCE_TYPE;
         }
      } else {
         logger.warn("Returning default ({}) for missing {} instance type", DEFAULT_INSTANCE_TYPE, instanceGroupType);
         return DEFAULT_INSTANCE_TYPE;
      }
   }

   private static int getInstanceCount(JsonNode root) {
      return root.has("instanceCount") ? root.get("instanceCount").asInt(1) : 1;
   }

   private static enum InstanceGroupType {
      MASTER,
      SLAVE;

      private InstanceGroupType() {
      }

      public String toString() {
         return super.toString().toLowerCase();
      }
   }
}
