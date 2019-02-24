package com.amazonaws.emr.node.provisioner.bigtop;

import com.amazonaws.emr.node.provisioner.puppet.api.PuppetException;

public interface Puppeteer {
   void applyPuppet() throws PuppetException;
}
