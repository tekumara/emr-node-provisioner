package com.amazonaws.emr.node.provisioner.util.filter;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class PrefixListFilter implements ListFilter<String> {
   private final List<String> prefixFilters;

   public PrefixListFilter(List<String> prefixFilters) {
      Preconditions.checkArgument(!prefixFilters.isEmpty(), "prefixFilters cannot be empty");
      this.prefixFilters = prefixFilters;
   }

   public List<String> filter(List<String> list) {
      Predicate<String> predicate = new Predicate<String>() {
         public boolean apply(String input) {
            Iterator var2 = PrefixListFilter.this.prefixFilters.iterator();

            String filter;
            do {
               if (!var2.hasNext()) {
                  return false;
               }

               filter = (String)var2.next();
            } while(!input.startsWith(filter));

            return true;
         }
      };
      return new ArrayList(Collections2.filter(list, predicate));
   }
}
