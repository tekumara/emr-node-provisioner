package com.amazonaws.emr.node.provisioner.bigtop.deploy.provider;

import com.amazonaws.emr.node.provisioner.bigtop.Deployer;

public interface DeployerProvider {
   Deployer provide();
}
