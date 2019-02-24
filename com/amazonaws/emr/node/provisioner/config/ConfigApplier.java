package com.amazonaws.emr.node.provisioner.config;

public interface ConfigApplier {
   void applyTo(Config var1) throws ConfigException;
}
