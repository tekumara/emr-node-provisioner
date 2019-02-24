package com.amazonaws.emr.node.provisioner.bigtop.hiera.data.generator;

import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import com.amazonaws.emr.node.provisioner.secure.password.config.PasswordConfigAppliers;
import java.util.Random;

public final class DataSourceGenerators {
   private DataSourceGenerators() {
   }

   public static DataSourceGenerator passwordGenerator(String dataSourceName, Random random) {
      ConfigApplier configApplier = PasswordConfigAppliers.defaultApplier(random);
      return ConfigDataSourceGenerator.newInstance(dataSourceName, configApplier);
   }
}
