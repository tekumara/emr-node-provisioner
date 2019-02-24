package com.amazonaws.emr.node.provisioner.config.spark;

final class HeapAndOverhead {
   final int heapSizeMB;
   final int overheadMB;

   HeapAndOverhead(int heapSizeMB, int overheadMB) {
      this.heapSizeMB = heapSizeMB;
      this.overheadMB = overheadMB;
   }
}
