package com.amazonaws.emr.node.provisioner.config;

import com.amazonaws.emr.node.provisioner.platform.model.Configuration;
import com.amazonaws.emr.node.provisioner.util.ResourceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;
import java.util.Map.Entry;
import org.apache.commons.lang3.tuple.Pair;

public final class ConfigFactory {
   private ConfigFactory() {
   }

   public static Config create(Collection<Configuration> configurations) {
      Config config = Config.empty();
      Iterator var2 = configurations.iterator();

      while(var2.hasNext()) {
         Configuration configuration = (Configuration)var2.next();
         applyConfiguration(configuration, config);
      }

      return config;
   }

   private static void applyConfiguration(Configuration source, Config destination) {
      Stack<Pair<Configuration, Config>> stack = new Stack();
      stack.push(Pair.of(source, destination));

      while(!stack.isEmpty()) {
         Pair<Configuration, Config> pair = (Pair)stack.pop();
         applyConfiguration(stack, (Configuration)pair.getLeft(), (Config)pair.getRight());
      }

   }

   private static void applyConfiguration(Stack<Pair<Configuration, Config>> stack, Configuration source, Config destination) {
      Preconditions.checkArgument(!destination.hasKey(source.getClassification()), String.format("Configuration '%s' has a naming conflict with either another configuration or property.", source.getClassification()));
      Config destinationChild = destination.putConfig(source.getClassification());
      Iterator var4 = source.getProperties().entrySet().iterator();

      while(var4.hasNext()) {
         Entry<String, String> property = (Entry)var4.next();
         destinationChild.put((String)property.getKey(), (String)property.getValue());
      }

      var4 = source.getConfigurations().iterator();

      while(var4.hasNext()) {
         Configuration nestedSource = (Configuration)var4.next();
         stack.push(Pair.of(nestedSource, destinationChild));
      }

   }

   public static Config createFromYaml(String yaml) throws IOException {
      ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
      return (Config)mapper.readValue(yaml, Config.class);
   }

   public static Config createFromYamlResource(String resourceName) {
      try {
         return createFromYaml(ResourceUtil.extractToString(resourceName));
      } catch (IOException var2) {
         throw new RuntimeException(String.format("Failed to extract resource %s to Config", resourceName), var2);
      }
   }
}
