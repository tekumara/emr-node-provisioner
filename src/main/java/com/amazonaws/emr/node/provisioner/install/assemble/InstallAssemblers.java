package com.amazonaws.emr.node.provisioner.install.assemble;

import com.amazonaws.emr.node.provisioner.install.manifest.InstallManifest;
import com.amazonaws.emr.node.provisioner.install.manifest.InstallManifestFactory;
import com.amazonaws.emr.node.provisioner.util.ResourceUtil;
import java.io.IOException;

public final class InstallAssemblers {
   private InstallAssemblers() {
   }

   public static InstallAssembler defaultAssembler() {
      String yaml = ResourceUtil.extractToString("/install-manifest.yaml");

      try {
         InstallManifest manifest = InstallManifestFactory.createFromYaml(yaml);
         return new InstallAssembler(manifest);
      } catch (IOException var2) {
         throw new RuntimeException("Failed to create default install manifest", var2);
      }
   }
}
