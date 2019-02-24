package com.amazonaws.emr.node.provisioner.install.manifest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;

public final class InstallManifestFactory {
   private InstallManifestFactory() {
   }

   public static InstallManifest createFromYaml(String yaml) throws IOException {
      ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
      return (InstallManifest)mapper.readValue(yaml, InstallManifest.class);
   }
}
