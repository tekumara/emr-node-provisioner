package com.amazonaws.emr.node.provisioner.util.filter;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public final class PatternListFilter implements ListFilter<String> {
   private final Predicate<CharSequence> predicate;

   public PatternListFilter(List<Pattern> patterns) {
      Preconditions.checkArgument(!patterns.isEmpty(), "Must supply at least one pattern.");
      this.predicate = buildPredicate(patterns);
   }

   public List<String> filter(List<String> list) {
      return new ArrayList(Collections2.filter(list, this.predicate));
   }

   private static Predicate<CharSequence> buildPredicate(List<Pattern> patterns) {
      List<Predicate<CharSequence>> predicates = new ArrayList();
      Iterator var2 = patterns.iterator();

      while(var2.hasNext()) {
         Pattern pattern = (Pattern)var2.next();
         predicates.add(Predicates.contains(pattern));
      }

      return Predicates.or(predicates);
   }
}
