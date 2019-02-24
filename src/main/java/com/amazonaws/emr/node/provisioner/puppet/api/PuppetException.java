package com.amazonaws.emr.node.provisioner.puppet.api;

public class PuppetException extends Exception {
   public PuppetException(String message, Throwable cause) {
      super(message, cause);
   }

   public PuppetException(String message) {
      super(message);
   }
}
