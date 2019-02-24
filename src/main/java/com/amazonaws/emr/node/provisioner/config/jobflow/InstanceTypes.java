package com.amazonaws.emr.node.provisioner.config.jobflow;

import com.amazonaws.emr.node.provisioner.pojo.InstanceType;
import com.amazonaws.emr.node.provisioner.util.ResourceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstanceTypes {
   static final Logger logger = LoggerFactory.getLogger(InstanceTypes.class);
   private static final String INSTANCE_TYPE_INFO_FILE = "/config/instance-type-info.json";
   private static List<InstanceType> instanceTypes;

   public InstanceTypes() {
   }

   public static void initialize() {
      if (instanceTypes == null) {
         ObjectMapper mapper = new ObjectMapper();

         try {
            TypeFactory typeFactory = mapper.getTypeFactory();
            CollectionType collectionType = typeFactory.constructCollectionType(List.class, InstanceType.class);
            instanceTypes = (List)mapper.readValue(ResourceUtil.extractToString("/config/instance-type-info.json"), collectionType);
         } catch (Exception var3) {
            logger.error(String.format("Failed to map %s", "/config/instance-type-info.json"));
            var3.printStackTrace();
         }
      }

   }

   public static List<InstanceType> getInstanceTypes() {
      initialize();
      return instanceTypes;
   }

   public static InstanceType getInstanceType(String instanceType) {
      initialize();
      Iterator var1 = instanceTypes.iterator();

      InstanceType it;
      do {
         if (!var1.hasNext()) {
            String errorMsg = String.format("Cannot get %s from instance types", instanceType);
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
         }

         it = (InstanceType)var1.next();
      } while(!it.getInstanceType().equals(instanceType));

      return it;
   }
}
