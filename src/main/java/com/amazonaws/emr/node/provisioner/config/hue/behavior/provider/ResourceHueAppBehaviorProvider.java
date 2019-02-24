package com.amazonaws.emr.node.provisioner.config.hue.behavior.provider;

import com.amazonaws.emr.node.provisioner.config.hue.behavior.HueAppBehavior;
import com.amazonaws.emr.node.provisioner.util.ResourceUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.util.List;

public final class ResourceHueAppBehaviorProvider implements HueAppBehaviorProvider {
   private final String resourceName;

   private ResourceHueAppBehaviorProvider(String resourceName) {
      this.resourceName = resourceName;
   }

   public static ResourceHueAppBehaviorProvider using(String resourceName) {
      return new ResourceHueAppBehaviorProvider(resourceName);
   }

   public List<HueAppBehavior> provide() {
      ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
      String yaml = ResourceUtil.extractToString(this.resourceName);

      try {
         return (List)mapper.readValue(yaml, new TypeReference<List<HueAppBehavior>>() {
         });
      } catch (IOException var4) {
         throw new RuntimeException(String.format("Unable to read HueAppBehaviors from resource %s", this.resourceName), var4);
      }
   }
}
