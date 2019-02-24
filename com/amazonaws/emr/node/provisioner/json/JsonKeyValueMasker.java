package com.amazonaws.emr.node.provisioner.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.api.client.util.Lists;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public final class JsonKeyValueMasker {
   private final ObjectMapper mapper = new ObjectMapper();
   private final String mask;
   private final Collection<Pattern> regexPatterns;

   public JsonKeyValueMasker(Collection<String> patterns, String mask) {
      this.regexPatterns = toPatterns(patterns);
      this.mask = mask;
   }

   public <T> T mask(T value, Class<T> klass) {
      JsonNode root = this.mapper.valueToTree(value);
      Stack<JsonNode> stack = new Stack();
      stack.push(root);

      while(true) {
         label39:
         while(!stack.isEmpty()) {
            JsonNode node = (JsonNode)stack.pop();
            Iterator var6;
            switch(node.getNodeType()) {
            case OBJECT:
               var6 = Lists.newArrayList(node.fields()).iterator();

               while(true) {
                  while(true) {
                     if (!var6.hasNext()) {
                        continue label39;
                     }

                     Entry<String, JsonNode> entry = (Entry)var6.next();
                     if (((JsonNode)entry.getValue()).isTextual() && this.hasPatternMatch((String)entry.getKey())) {
                        entry.setValue(new TextNode(this.mask));
                     } else {
                        stack.push(entry.getValue());
                     }
                  }
               }
            case ARRAY:
               var6 = Lists.newArrayList(node.elements()).iterator();

               while(var6.hasNext()) {
                  JsonNode n = (JsonNode)var6.next();
                  stack.push(n);
               }
            }
         }

         return this.treeToValue(root, klass);
      }
   }

   private <T> T treeToValue(JsonNode tree, Class<T> klass) {
      try {
         return this.mapper.treeToValue(tree, klass);
      } catch (JsonProcessingException var4) {
         throw new RuntimeException(var4);
      }
   }

   private boolean hasPatternMatch(String value) {
      Iterator var2 = this.regexPatterns.iterator();

      Pattern p;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         p = (Pattern)var2.next();
      } while(!p.matcher(value).find());

      return true;
   }

   private static Collection<Pattern> toPatterns(Collection<String> regexPatterns) {
      Iterable<Pattern> transformed = Iterables.transform(regexPatterns, new Function<String, Pattern>() {
         @Nullable
         public Pattern apply(String input) {
            return Pattern.compile(input, 2);
         }
      });
      return Lists.newArrayList(transformed);
   }
}
