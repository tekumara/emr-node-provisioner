package com.amazonaws.emr.node.provisioner.puppet.profiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PuppetParser {
   private static final String DEFAULT_PATTERN_1 = "Info: /?(?:.+/)?(.+)\\[(.+)\\]: Evaluated in (\\d+\\.\\d+) seconds";
   private static final String DEFAULT_PATTERN_2 = "Notice: Finished catalog run in (\\d+\\.\\d+) seconds";
   private final Pattern pattern = Pattern.compile("Info: /?(?:.+/)?(.+)\\[(.+)\\]: Evaluated in (\\d+\\.\\d+) seconds");
   private final Pattern patternEnd = Pattern.compile("Notice: Finished catalog run in (\\d+\\.\\d+) seconds");
   private Matcher matcher;
   private Matcher matcherEnd;

   public PuppetParser() {
   }

   private boolean matches(String toMatch) {
      this.matcher = this.pattern.matcher(toMatch);
      return this.matcher.matches();
   }

   private boolean totalRunMatch(String toMatch) {
      this.matcherEnd = this.patternEnd.matcher(toMatch);
      return this.matcherEnd.matches();
   }

   public Map<String, ExecutedTask> readAndParse(File inputFile) {
      HashMap logEntries = new HashMap();

      try {
         BufferedReader buffReader = new BufferedReader(new FileReader(inputFile));
         Throwable var5 = null;

         try {
            String line;
            try {
               while((line = buffReader.readLine()) != null) {
                  if (this.matches(line)) {
                     String key = this.matcher.group(1) + "[" + this.matcher.group(2) + "]";
                     ExecutedTask entry = new ExecutedTask(this.matcher.group(1), this.matcher.group(2), Float.parseFloat(this.matcher.group(3)));
                     logEntries.put(key, entry);
                  } else if (this.totalRunMatch(line)) {
                     ExecutedTask entry = new ExecutedTask("Catalog", "totalRunTime", Float.parseFloat(this.matcherEnd.group(1)));
                     logEntries.put("Catalog[totalRunTime]", entry);
                  }
               }
            } catch (Throwable var16) {
               var5 = var16;
               throw var16;
            }
         } finally {
            if (buffReader != null) {
               if (var5 != null) {
                  try {
                     buffReader.close();
                  } catch (Throwable var15) {
                     var5.addSuppressed(var15);
                  }
               } else {
                  buffReader.close();
               }
            }

         }

         return logEntries;
      } catch (IOException var18) {
         throw new RuntimeException(var18);
      }
   }
}
