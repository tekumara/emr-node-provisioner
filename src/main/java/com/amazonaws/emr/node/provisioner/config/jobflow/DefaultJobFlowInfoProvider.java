package com.amazonaws.emr.node.provisioner.config.jobflow;

import java.io.FileInputStream;
import java.io.IOException;

public final class DefaultJobFlowInfoProvider implements JobFlowInfoProvider {
   private static final String JOB_FLOW_INFO_DEFAULT_PATH = "/mnt/var/lib/info/job-flow.json";

   public DefaultJobFlowInfoProvider() {
   }

   public JobFlowInfo provide() {
      try {
         FileInputStream is = new FileInputStream("/mnt/var/lib/info/job-flow.json");
         Throwable var2 = null;

         JobFlowInfo var3;
         try {
            var3 = JobFlowInfoFactory.getJobFlowInfo(is);
         } catch (Throwable var13) {
            var2 = var13;
            throw var13;
         } finally {
            if (is != null) {
               if (var2 != null) {
                  try {
                     is.close();
                  } catch (Throwable var12) {
                     var2.addSuppressed(var12);
                  }
               } else {
                  is.close();
               }
            }

         }

         return var3;
      } catch (IOException var15) {
         throw new RuntimeException(var15);
      }
   }
}
