package com.amazonaws.emr.node.provisioner.util;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.primitives.Booleans;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;

public final class Optionals {
   private Optionals() {
   }

   public static boolean isOnlyOnePresent(Optional requiredOptional, Optional... optionals) {
      List<Boolean> isPresents = Lists.newArrayList();
      isPresents.add(requiredOptional.isPresent());
      Optional[] var3 = optionals;
      int var4 = optionals.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Optional optional = var3[var5];
         isPresents.add(optional.isPresent());
      }

      return BooleanUtils.xor(Booleans.toArray(isPresents));
   }
}
