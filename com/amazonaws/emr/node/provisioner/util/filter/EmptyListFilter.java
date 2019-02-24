package com.amazonaws.emr.node.provisioner.util.filter;

import java.util.Collections;
import java.util.List;

public final class EmptyListFilter implements ListFilter<String> {
   public EmptyListFilter() {
   }

   public List<String> filter(List<String> list) {
      return Collections.emptyList();
   }
}
