package com.amazonaws.emr.node.provisioner.config.spark;

interface SparkConstants {
   String SPARK_ON_YARN_COMPONENT = "spark-on-yarn";
   String SPARK_CLASSIFICATION = "spark";
   String SPARK_DEFAULTS_CLASSIFICATION = "spark-defaults";
   String MAXIMIZE_RESOURCE_ALLOCATION = "maximizeResourceAllocation";
   String SPARK_DEFAULTS_OVERRIDES = "spark::common::spark_defaults_overrides";
   String DYNAMIC_ALLOCATION = "spark.dynamicAllocation.enabled";
   String DRIVER_MEMORY = "spark.driver.memory";
   String EXECUTOR_INSTANCES = "spark.executor.instances";
   String EXECUTOR_CORES = "spark.executor.cores";
   String EXECUTOR_MEMORY = "spark.executor.memory";
   String YARN_AM_MEMORY = "spark.yarn.am.memory";
   String DEFAULT_PARALLELISM = "spark.default.parallelism";
   int DEFAULT_YARN_AM_MEMORY = 512;
   int MIN_PARALLELISM = 2;
   int PARALLELISM_FACTOR = 2;
   int EXECUTORS_PER_INSTANCE = 1;
   int YARN_MIN_MEMORY_MB = 32;
   double DEFAULT_MEMORY_OVERHEAD = 0.1D;
   int MIN_MEMORY_OVERHEAD_MB = 384;
   int DEFAULT_DRIVER_HEAP_SIZE_MB = 1024;
   int DEFAULT_EXECUTOR_HEAP_SIZE_MB = 1024;
   int MIN_SPARK_HEAP_SIZE_MB = 465;
}
