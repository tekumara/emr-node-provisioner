package com.amazonaws.emr.node.provisioner;

import com.amazonaws.emr.node.provisioner.workflow.NodeProvisionerWorkflow;
import com.amazonaws.emr.node.provisioner.workflow.NodeProvisionerWorkflowOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Program {
   private static final Logger logger = LoggerFactory.getLogger(Program.class);

   public Program() {
   }

   public static void main(String[] args) {
      try {
         ProgramOptions programOptions = (new ProgramOptionsParser()).parse(args);
         NodeProvisionerWorkflowOptions workflowOptions = (new NodeProvisionerWorkflowOptions()).withProvisionYumRepo(programOptions.isProvisionRepo()).withProvision(programOptions.isProvision()).withInstall(programOptions.isInstall()).withComponentNames(programOptions.getComponentNames());
         (new NodeProvisionerWorkflow()).work(workflowOptions);
      } catch (Throwable var3) {
         logger.error("Encountered a problem while provisioning", var3);
         System.exit(-1);
      }

   }
}
