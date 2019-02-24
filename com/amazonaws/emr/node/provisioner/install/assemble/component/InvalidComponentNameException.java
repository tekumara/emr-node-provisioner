package com.amazonaws.emr.node.provisioner.install.assemble.component;

public class InvalidComponentNameException extends RuntimeException {
   public InvalidComponentNameException(String message) {
      super(message);
   }

   public InvalidComponentNameException(String message, Throwable cause) {
      super(message, cause);
   }
}
