# EMR Node Provisioner

The AWS EMR Node Provisioner (`usr/share/aws/emr/node-provisioner/`) runs on startup to provision [apache bigtop](https://github.com/apache/bigtop) on the EMR cluster. Logs are written to `/mnt/var/log/provision-node/`.

This is a [fernflower](https://github.com/fesh0r/fernflower) decompiled version of `node-provisioner-2.18.0.jar`, to understand the settings used by EMR which haven't been documented by AWS.

In particular, when `maximizeResourceAllocation` is `false`, `spark.executor.cores` and `spark.executor.memory` will be determined by the instance type as per [config/instance-type-info.json](config/instance-type-info.json). 