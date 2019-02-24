package com.amazonaws.emr.node.provisioner.util;

import java.io.File;

public final class FileUtil {
   private FileUtil() {
   }

   public static boolean isRoot(File file) {
      return file.isAbsolute() && file.getParent() == null;
   }
}
