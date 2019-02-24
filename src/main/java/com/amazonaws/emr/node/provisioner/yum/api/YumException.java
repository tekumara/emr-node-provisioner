package com.amazonaws.emr.node.provisioner.yum.api;

public class YumException extends Exception {
   public YumException(String message) {
      super(message);
   }

   public YumException(String message, Throwable cause) {
      super(message, cause);
   }
}
