package com.amazonaws.emr.node.provisioner.puppet.profiler;

public final class ExecutedTask {
   public String resource;
   public String resourceID;
   public float timeEvaluated;

   private ExecutedTask() {
   }

   public ExecutedTask(String resource, String resourceID, float timeEvaluated) {
      this.resource = resource;
      this.resourceID = resourceID;
      this.timeEvaluated = timeEvaluated;
   }
}
