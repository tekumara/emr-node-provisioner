package com.amazonaws.emr.node.provisioner.install.packaging;

public class InvalidPackageGroupNameException extends RuntimeException {
   public InvalidPackageGroupNameException(String message) {
      super(message);
   }

   public InvalidPackageGroupNameException(String message, Throwable cause) {
      super(message, cause);
   }
}
