package com.amazonaws.emr.node.provisioner.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.List;

public final class ConfigPath implements Iterable<String> {
   private final List<String> keys;

   public ConfigPath(String... keys) {
      this.keys = ImmutableList.copyOf(keys);
   }

   @JsonCreator
   public ConfigPath(String key) {
      this.keys = ImmutableList.of(key);
   }

   @JsonCreator
   public ConfigPath(List<String> keys) {
      this.keys = ImmutableList.copyOf(keys);
   }

   public boolean isRoot() {
      return this.keys.isEmpty();
   }

   public ConfigPath getParent() throws ConfigException {
      if (this.isRoot()) {
         throw new ConfigException("Root path does not have a parent.");
      } else {
         return new ConfigPath(this.keys.subList(0, this.keys.size() - 1));
      }
   }

   public String getLastKey() throws ConfigException {
      if (this.isRoot()) {
         throw new ConfigException("Root path does not have any keys.");
      } else {
         return (String)this.keys.get(this.keys.size() - 1);
      }
   }

   public Iterator<String> iterator() {
      return this.keys.iterator();
   }

   public String toString() {
      return this.keys.toString();
   }
}
