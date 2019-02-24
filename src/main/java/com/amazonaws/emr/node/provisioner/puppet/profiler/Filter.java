package com.amazonaws.emr.node.provisioner.puppet.profiler;

import java.util.Map;

public interface Filter<K, V> {
   Map<K, V> filteredResults(Map<K, V> var1);
}
