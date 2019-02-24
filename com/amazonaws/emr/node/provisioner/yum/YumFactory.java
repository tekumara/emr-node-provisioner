package com.amazonaws.emr.node.provisioner.yum;

import com.amazonaws.emr.node.provisioner.yum.api.Yum;
import com.amazonaws.emr.node.provisioner.yum.api.YumOptions;

public final class YumFactory {
   private static final String DEFAULT_EXECUTABLE = "yum";
   private final String executable;

   public YumFactory() {
      this("yum");
   }

   public YumFactory(String executable) {
      this.executable = executable;
   }

   public Yum create() {
      return new Yum(this.executable);
   }

   public Yum create(YumOptions options) {
      return new Yum(this.executable, options);
   }
}
