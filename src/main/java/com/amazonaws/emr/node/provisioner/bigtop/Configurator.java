package com.amazonaws.emr.node.provisioner.bigtop;

import com.amazonaws.emr.node.provisioner.config.Config;
import java.io.IOException;

public interface Configurator {
   void configure(Config var1) throws IOException;

   void cleanUp() throws IOException;
}
