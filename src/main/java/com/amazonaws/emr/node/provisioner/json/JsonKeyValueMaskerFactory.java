package com.amazonaws.emr.node.provisioner.json;

import com.amazonaws.emr.node.provisioner.secure.LoggingMaskRegexProvider;
import java.util.Collection;

public final class JsonKeyValueMaskerFactory {
   private static final String DEFAULT_MASK = "******";
   private static final Collection<String> loggingMaskRegexPatterns = LoggingMaskRegexProvider.newInstance().provideDefault();

   private JsonKeyValueMaskerFactory() {
   }

   public static JsonKeyValueMasker createDefault() {
      return new JsonKeyValueMasker(loggingMaskRegexPatterns, "******");
   }
}
