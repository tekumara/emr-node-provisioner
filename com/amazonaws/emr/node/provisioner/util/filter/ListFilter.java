package com.amazonaws.emr.node.provisioner.util.filter;

import java.util.List;

public interface ListFilter<T> {
   List<T> filter(List<T> var1);
}
