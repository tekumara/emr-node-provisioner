package com.amazonaws.emr.node.provisioner.pojo;

public class InstanceType {
   private String instanceType;
   private String instanceFamily;
   private int yarnMaxMemoryMB;
   private int vcores;
   private int sparkOptimalExecutorMemory;
   private int sparkOptimalExecutorCores;

   public InstanceType() {
   }

   public String getInstanceType() {
      return this.instanceType;
   }

   public void setInstanceType(String instanceType) {
      this.instanceType = instanceType;
   }

   public String getInstanceFamily() {
      return this.instanceFamily;
   }

   public void setInstanceFamily(String instanceFamily) {
      this.instanceFamily = instanceFamily;
   }

   public int getYarnMaxMemoryMB() {
      return this.yarnMaxMemoryMB;
   }

   public void setYarnMaxMemoryMB(int yarnMaxMemoryMB) {
      this.yarnMaxMemoryMB = yarnMaxMemoryMB;
   }

   public int getVcores() {
      return this.vcores;
   }

   public void setVcores(int vcores) {
      this.vcores = vcores;
   }

   public int getSparkOptimalExecutorMemory() {
      return this.sparkOptimalExecutorMemory;
   }

   public void setSparkOptimalExecutorMemory(int sparkOptimalExecutorMemory) {
      this.sparkOptimalExecutorMemory = sparkOptimalExecutorMemory;
   }

   public int getSparkOptimalExecutorCores() {
      return this.sparkOptimalExecutorCores;
   }

   public void setSparkOptimalExecutorCores(int sparkOptimalExecutorCores) {
      this.sparkOptimalExecutorCores = sparkOptimalExecutorCores;
   }

   public String toString() {
      return String.format("InstanceType [instanceType=%s, instanceFamily=%s, yarnMaxMemoryMB=%d, vcores=%d, sparkOptimalExecutorMemory=%d, sparkOptimalExecutorCores=%d]", this.instanceType, this.instanceFamily, this.yarnMaxMemoryMB, this.vcores, this.sparkOptimalExecutorMemory, this.sparkOptimalExecutorCores);
   }
}
