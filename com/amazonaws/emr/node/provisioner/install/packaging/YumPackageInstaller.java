package com.amazonaws.emr.node.provisioner.install.packaging;

import com.amazonaws.emr.node.provisioner.yum.YumFactory;
import com.amazonaws.emr.node.provisioner.yum.api.YumException;
import com.amazonaws.emr.node.provisioner.yum.api.YumOptions;
import java.io.IOException;
import java.util.Collection;

public final class YumPackageInstaller implements PackageInstaller {
   private final YumFactory yumFactory;

   public YumPackageInstaller() {
      this(new YumFactory());
   }

   public YumPackageInstaller(YumFactory yumFactory) {
      this.yumFactory = yumFactory;
   }

   public void install(Collection<String> packages) {
      try {
         YumOptions yumOptions = (new YumOptions()).withAssumeYes(true);
         this.yumFactory.create(yumOptions).install().withPackages(packages).call();
      } catch (YumException | IOException var3) {
         throw new RuntimeException("Failed to install packages: " + packages, var3);
      }
   }
}
