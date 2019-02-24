package com.amazonaws.emr.node.provisioner.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public final class Patterns {
   private Patterns() {
   }

   public static List<Pattern> compileMany(List<String> regexes) {
      List<Pattern> patterns = new ArrayList();
      Iterator var2 = regexes.iterator();

      while(var2.hasNext()) {
         String regex = (String)var2.next();
         patterns.add(Pattern.compile(regex));
      }

      return patterns;
   }
}
