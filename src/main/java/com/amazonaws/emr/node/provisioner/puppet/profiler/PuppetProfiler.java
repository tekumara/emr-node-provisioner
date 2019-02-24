package com.amazonaws.emr.node.provisioner.puppet.profiler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class PuppetProfiler implements Profiler<Filter<String, ExecutedTask>, String, ExecutedTask> {
   private final PuppetParser parser = new PuppetParser();

   public PuppetProfiler() {
   }

   public void profile(File inputFile, File outputFile) throws IOException {
      this.dumpResults(outputFile, this.profileMap(inputFile));
   }

   public void profile(File inputFile, File outputFile, ArrayList<Filter<String, ExecutedTask>> filters) throws IOException {
      this.dumpResults(outputFile, this.profileMap(inputFile, filters));
   }

   public Map<String, ExecutedTask> profileMap(File inputFile) throws IOException {
      return this.parser.readAndParse(inputFile);
   }

   public Map<String, ExecutedTask> profileMap(File inputFile, ArrayList<Filter<String, ExecutedTask>> filters) throws IOException {
      Map<String, ExecutedTask> inputMap = this.profileMap(inputFile);
      Map<String, ExecutedTask> filterMap = new HashMap();
      Iterator var5 = filters.iterator();

      while(var5.hasNext()) {
         Filter<String, ExecutedTask> filter = (Filter)var5.next();
         filterMap.putAll(filter.filteredResults(inputMap));
      }

      return filterMap;
   }

   public void dumpResults(File outputFile, Map<String, ExecutedTask> entryMap) {
      try {
         FileWriter writer = new FileWriter(outputFile);
         Throwable var4 = null;

         try {
            ObjectMapper mapper = new ObjectMapper();
            Iterator var6 = entryMap.entrySet().iterator();

            while(var6.hasNext()) {
               Entry<String, ExecutedTask> entry = (Entry)var6.next();
               writer.write(mapper.writeValueAsString(entry.getValue()));
            }

            writer.close();
         } catch (Throwable var16) {
            var4 = var16;
            throw var16;
         } finally {
            if (writer != null) {
               if (var4 != null) {
                  try {
                     writer.close();
                  } catch (Throwable var15) {
                     var4.addSuppressed(var15);
                  }
               } else {
                  writer.close();
               }
            }

         }
      } catch (IOException var18) {
         throw new RuntimeException(var18);
      }
   }
}
