package com.amazonaws.emr.node.provisioner.config.applier;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import com.amazonaws.emr.node.provisioner.config.ConfigException;

public final class NoOperationConfigApplier implements ConfigApplier {
   public NoOperationConfigApplier() {
   }

   public void applyTo(Config config) throws ConfigException {
   }
}
