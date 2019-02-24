package com.amazonaws.emr.node.provisioner.puppet.profiler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class ResourceIDFilter implements Filter<String, ExecutedTask> {
   private final String filter;

   public ResourceIDFilter(String newFilter) {
      this.filter = newFilter;
   }

   public Map<String, ExecutedTask> filteredResults(Map<String, ExecutedTask> inputMap) {
      Map<String, ExecutedTask> filteredEntries = new HashMap();
      Iterator var3 = inputMap.entrySet().iterator();

      while(var3.hasNext()) {
         Entry<String, ExecutedTask> entry = (Entry)var3.next();
         ExecutedTask filteredEntry = (ExecutedTask)entry.getValue();
         if (this.filter.equals(filteredEntry.resourceID)) {
            filteredEntries.put(entry.getKey(), filteredEntry);
         }
      }

      return filteredEntries;
   }
}
