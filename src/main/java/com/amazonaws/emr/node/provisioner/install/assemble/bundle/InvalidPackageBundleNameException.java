package com.amazonaws.emr.node.provisioner.install.assemble.bundle;

public class InvalidPackageBundleNameException extends RuntimeException {
   public InvalidPackageBundleNameException(String message) {
      super(message);
   }

   public InvalidPackageBundleNameException(String message, Throwable cause) {
      super(message, cause);
   }
}
