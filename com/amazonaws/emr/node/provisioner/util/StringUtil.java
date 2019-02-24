package com.amazonaws.emr.node.provisioner.util;

public final class StringUtil {
   private StringUtil() {
   }

   public static String indent(String value) {
      return value.replaceAll("(?m)^", "  ");
   }
}
