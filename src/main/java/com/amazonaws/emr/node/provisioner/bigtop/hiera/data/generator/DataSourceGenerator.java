package com.amazonaws.emr.node.provisioner.bigtop.hiera.data.generator;

import com.amazonaws.emr.node.provisioner.bigtop.hiera.data.DataSource;

public interface DataSourceGenerator {
   DataSource generate();
}
