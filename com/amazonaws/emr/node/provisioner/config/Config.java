package com.amazonaws.emr.node.provisioner.config;

import com.amazonaws.emr.node.provisioner.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.Map.Entry;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Config {
   private static final Logger logger = LoggerFactory.getLogger(Config.class);
   private final ObjectNode node;
   private final ObjectMapper mapper;

   private Config() {
      this(new ObjectMapper());
   }

   @JsonCreator
   private Config(ObjectNode node) {
      this(node, new ObjectMapper());
   }

   private Config(ObjectMapper mapper) {
      this(mapper.createObjectNode(), mapper);
   }

   private Config(ObjectNode node, ObjectMapper mapper) {
      this.node = node;
      this.mapper = mapper;
   }

   public static Config empty() {
      return new Config();
   }

   public static Config of(ObjectNode node) {
      return new Config(node.deepCopy());
   }

   public Map<String, Object> unwrapped() {
      try {
         return (Map)this.mapper.readValue(this.mapper.treeAsTokens(this.node), new TypeReference<HashMap<String, Object>>() {
         });
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   public Set<String> keys() {
      return Sets.newHashSet(this.node.fieldNames());
   }

   public boolean isEmpty() {
      return !this.node.fieldNames().hasNext();
   }

   public boolean hasKey(String key) {
      return this.hasKey(new ConfigPath(key));
   }

   public boolean hasKey(ConfigPath path) {
      return !this.resolveNode(path).isMissingNode();
   }

   public String getString(String key) throws ConfigException {
      return this.getString(new ConfigPath(key));
   }

   public String getString(ConfigPath path) throws ConfigException {
      JsonNode thisNode = this.resolveNode(path);
      if (!thisNode.isValueNode()) {
         throw new ConfigException("Key at " + path + " not found or cannot be represented as a string.");
      } else {
         return thisNode.asText();
      }
   }

   public boolean getBoolean(String key) throws ConfigException {
      return this.getBoolean(new ConfigPath(key));
   }

   public boolean getBoolean(ConfigPath path) throws ConfigException {
      JsonNode thisNode = this.resolveNode(path);
      if (!thisNode.isValueNode()) {
         throw new ConfigException("Key at " + path + " not found or cannot be represented as a boolean.");
      } else {
         return thisNode.asBoolean();
      }
   }

   public Config getConfig(String key) throws ConfigException {
      return this.getConfig(new ConfigPath(key));
   }

   public Config getConfig(ConfigPath path) throws ConfigException {
      JsonNode thisNode = this.resolveNode(path);
      if (!thisNode.isObject()) {
         throw new ConfigException("Key at " + path + " not found or is not a config object.");
      } else {
         return new Config((ObjectNode)thisNode);
      }
   }

   public List<String> getStringList(String key) throws ConfigException {
      return this.getStringList(new ConfigPath(key));
   }

   public List<String> getStringList(ConfigPath path) throws ConfigException {
      JsonNode thisNode = this.resolveNode(path);
      if (!thisNode.isArray()) {
         throw new ConfigException("Key at " + path + " not found or is not a string list.");
      } else {
         List<String> list = new ArrayList();
         Iterator var4 = thisNode.iterator();

         while(var4.hasNext()) {
            JsonNode child = (JsonNode)var4.next();
            list.add(child.asText());
         }

         return list;
      }
   }

   public void put(String key, String value) {
      this.node.put(key, value);
   }

   public void put(ConfigPath path, String value) throws ConfigException {
      this.resolveConfig(path.getParent()).put(path.getLastKey(), value);
   }

   public void put(String key, boolean value) {
      this.node.put(key, value);
   }

   public void put(ConfigPath path, boolean value) throws ConfigException {
      this.resolveConfig(path.getParent()).put(path.getLastKey(), value);
   }

   public void put(String key, Config value) {
      this.node.set(key, value.node);
   }

   public void put(ConfigPath path, Iterable<String> list) throws ConfigException {
      this.resolveConfig(path.getParent()).put(path.getLastKey(), list);
   }

   public void put(String key, Iterable<String> list) {
      ArrayNode array = this.node.putArray(key);
      Iterator var4 = list.iterator();

      while(var4.hasNext()) {
         String string = (String)var4.next();
         array.add(string);
      }

   }

   public Config putConfig(String key) {
      return new Config(this.node.putObject(key));
   }

   public Config resolveConfig(String key) throws ConfigException {
      return this.resolveConfig(new ConfigPath(key));
   }

   public Config resolveConfig(ConfigPath path) throws ConfigException {
      Config thisConfig = this;
      Iterator var3 = path.iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (thisConfig.hasKey(key)) {
            thisConfig = thisConfig.getConfig(key);
         } else {
            thisConfig = thisConfig.putConfig(key);
         }
      }

      return thisConfig;
   }

   private JsonNode resolveNode(ConfigPath path) {
      JsonNode thisNode = this.node;

      String key;
      for(Iterator var3 = path.iterator(); var3.hasNext(); thisNode = ((JsonNode)thisNode).path(key)) {
         key = (String)var3.next();
      }

      return (JsonNode)thisNode;
   }

   public void remove(String key) {
      this.node.remove(key);
   }

   public void mergeWithOverride(Config other) {
      merge(this.node, other.node, true);
   }

   public void merge(Config other) {
      merge(this.node, other.node, false);
   }

   private static void merge(ObjectNode primary, ObjectNode secondary, boolean override) {
      Stack<Pair<ObjectNode, ObjectNode>> stack = new Stack();
      stack.push(Pair.of(primary, secondary));

      while(!stack.isEmpty()) {
         Pair<ObjectNode, ObjectNode> pair = (Pair)stack.pop();
         merge(stack, (ObjectNode)pair.getLeft(), (ObjectNode)pair.getRight(), override);
      }

   }

   private static void merge(Stack<Pair<ObjectNode, ObjectNode>> stack, ObjectNode primary, ObjectNode secondary, boolean override) {
      Iterator fieldNamesItr = secondary.fieldNames();

      while(true) {
         while(fieldNamesItr.hasNext()) {
            String fieldName = (String)fieldNamesItr.next();
            JsonNode primaryNode = primary.get(fieldName);
            JsonNode secondaryNode = secondary.get(fieldName);
            if (primaryNode == null) {
               primary.set(fieldName, secondaryNode.deepCopy());
            } else if (primaryNode.isObject() && secondaryNode.isObject()) {
               stack.push(Pair.of((ObjectNode)primaryNode, (ObjectNode)secondaryNode));
            } else {
               if (primaryNode.isObject() != secondaryNode.isObject()) {
                  String action = override ? "overriding with" : "ignoring";
                  logger.debug("isObject mismatch for key {}: {} secondaryNode. primaryNode.isObject={}, secondaryNode.isObject={}", new Object[]{fieldName, action, primaryNode.isObject(), secondaryNode.isObject()});
               }

               if (override) {
                  primary.set(fieldName, secondaryNode.deepCopy());
               }
            }
         }

         return;
      }
   }

   public Config deepCopy() {
      return of(this.node);
   }

   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         Config other = (Config)object;
         return Objects.equals(this.node, other.node);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.node.hashCode();
   }

   public String toString() {
      try {
         return JsonUtil.prettyDump((JsonNode)this.node);
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   private ImmutableSet<Entry<String, JsonNode>> difference(Config other, boolean symmetric) {
      Set theseFields = Sets.newHashSet(this.node.fields());
      Set otherFields = Sets.newHashSet(other.node.fields());
      SetView diff;
      if (symmetric) {
         diff = Sets.symmetricDifference(theseFields, otherFields);
      } else {
         diff = Sets.difference(theseFields, otherFields);
      }

      return diff.immutableCopy();
   }

   public ImmutableSet<Entry<String, JsonNode>> difference(Config other) {
      return this.difference(other, false);
   }

   public ImmutableSet<Entry<String, JsonNode>> symmetricDifference(Config other) {
      return this.difference(other, true);
   }
}
