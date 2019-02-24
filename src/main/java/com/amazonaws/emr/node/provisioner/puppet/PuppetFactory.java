package com.amazonaws.emr.node.provisioner.puppet;

import com.amazonaws.emr.node.provisioner.puppet.api.Puppet;
import com.amazonaws.emr.node.provisioner.puppet.api.PuppetOptions;

public final class PuppetFactory {
   private static final String DEFAULT_EXECUTABLE = "puppet";
   private final String executable;

   public PuppetFactory() {
      this("puppet");
   }

   public PuppetFactory(String executable) {
      this.executable = executable;
   }

   public Puppet create() {
      return new Puppet(this.executable);
   }

   public Puppet create(PuppetOptions options) {
      return new Puppet(this.executable, options);
   }
}
