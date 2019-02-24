package com.amazonaws.emr.node.provisioner.util.preconditions;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.text.WordUtils;

public final class CommonPreconditions {
   private CommonPreconditions() {
   }

   public static <T> void require(T reference, String name) {
      Preconditions.checkNotNull(reference, "%s is required", new Object[]{WordUtils.capitalize(name)});
   }
}
