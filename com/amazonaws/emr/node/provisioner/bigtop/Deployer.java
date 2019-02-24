package com.amazonaws.emr.node.provisioner.bigtop;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.puppet.api.PuppetException;
import java.io.IOException;

public interface Deployer {
   void deploy(Config var1) throws IOException, PuppetException;
}
