package com.amazonaws.emr.node.provisioner.util.filter;

import com.amazonaws.emr.node.provisioner.util.Patterns;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.regex.Pattern;

public final class ComponentFilters {
   private static final String HADOOP_REGEX = "^hadoop-";
   private static final List<String> hadoopPluginRegexs = ImmutableList.of("^emrfs$", "^emr-kinesis$", "^emr-ddb$", "^emr-goodies$", "^kerberos-.+$");
   private static final List<Pattern> hadoopWithPluginsPatterns;

   private ComponentFilters() {
   }

   public static ListFilter<String> hadoopWithPluginsFilter() {
      return new PatternListFilter(hadoopWithPluginsPatterns);
   }

   static {
      hadoopWithPluginsPatterns = Patterns.compileMany(ImmutableList.builder().add("^hadoop-").addAll(hadoopPluginRegexs).build());
   }
}
