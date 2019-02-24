package com.amazonaws.emr.node.provisioner.bigtop.hiera.data;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.util.YamlUtil;

public final class DataSources {
   private DataSources() {
   }

   public static DataSource yamlBacked(String name, Config config) {
      String yamlContent = YamlUtil.dump(config, YamlUtil.ScalarStyle.SINGLE_QUOTED);
      return yamlBacked(name, yamlContent);
   }

   public static DataSource yamlBacked(String name, String yamlContent) {
      return DataSource.newInstance(name, yamlContent, DataSourceBackend.YAML);
   }
}
