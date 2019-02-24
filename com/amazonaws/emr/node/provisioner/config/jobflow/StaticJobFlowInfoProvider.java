package com.amazonaws.emr.node.provisioner.config.jobflow;

public final class StaticJobFlowInfoProvider implements JobFlowInfoProvider {
   private final JobFlowInfo info;

   public StaticJobFlowInfoProvider(JobFlowInfo info) {
      this.info = info;
   }

   public JobFlowInfo provide() {
      return this.info;
   }
}
