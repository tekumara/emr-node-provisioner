package com.amazonaws.emr.node.provisioner.util.filter;

import java.util.ArrayList;
import java.util.List;

public final class PassThroughListFilter implements ListFilter<String> {
   public PassThroughListFilter() {
   }

   public List<String> filter(List<String> list) {
      return new ArrayList(list);
   }
}
