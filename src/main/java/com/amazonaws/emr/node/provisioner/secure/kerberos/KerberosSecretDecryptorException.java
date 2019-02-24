package com.amazonaws.emr.node.provisioner.secure.kerberos;

public class KerberosSecretDecryptorException extends RuntimeException {
   public KerberosSecretDecryptorException(String message) {
      super(message);
   }

   public KerberosSecretDecryptorException(String message, Throwable throwable) {
      super(message, throwable);
   }
}
