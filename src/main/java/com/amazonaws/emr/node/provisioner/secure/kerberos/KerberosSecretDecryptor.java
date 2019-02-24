package com.amazonaws.emr.node.provisioner.secure.kerberos;

import com.google.common.annotations.VisibleForTesting;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.SecretKeyEntry;
import java.util.Base64;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class KerberosSecretDecryptor {
   private static final String TRANSFORMATION_TYPE = "AES";
   private static final String SECRET_KEY_ALIAS = "kerberos_secretkey";
   private static final String DEFAULT_KERBEROS_KEYSTORE_PATH = "/emr/instance-controller/lib/kerberos_keystore";
   private static final String PASSWORD_SOURCE = "abcdefgijkmnopqrstwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
   private static final int PASSWORD_LENGTH = 18;
   private SecretKey secretKey;

   public KerberosSecretDecryptor(String seedSource) {
      this.secretKey = this.getSecretKey(seedSource, "/emr/instance-controller/lib/kerberos_keystore");
   }

   @VisibleForTesting
   public KerberosSecretDecryptor(SecretKey secretKey) {
      this.secretKey = secretKey;
   }

   public String decryptText(String byteCipherText) {
      byte[] bytePlainText = null;

      try {
         Cipher aesCipher = Cipher.getInstance("AES");
         aesCipher.init(2, this.secretKey);
         byte[] decodedText = Base64.getDecoder().decode(byteCipherText);
         bytePlainText = aesCipher.doFinal(decodedText);
      } catch (Exception var5) {
         this.handleException("Error in decrypting kerberos secret", var5);
      }

      return new String(bytePlainText);
   }

   @VisibleForTesting
   public long generateSeed(String seedSource) {
      char[] seedChars = seedSource.toCharArray();
      long seedValue = 0L;
      char[] var5 = seedChars;
      int var6 = seedChars.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         char ch = var5[var7];
         seedValue += (long)ch;
      }

      return seedValue;
   }

   @VisibleForTesting
   public String generatePassword(String seedSource) {
      return this.generatePassword(this.generateSeed(seedSource));
   }

   private String generatePassword(long seed) {
      StringBuilder password = new StringBuilder();
      Random random = new Random(seed);

      while(password.length() < 18) {
         int index = Math.abs(random.nextInt() * (password.length() + 1) % "abcdefgijkmnopqrstwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".length());
         password.append("abcdefgijkmnopqrstwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".charAt(index));
      }

      return password.toString();
   }

   private SecretKey getSecretKey(String seedSource, String keyStorePath) {
      SecretKey secretKey = null;

      try {
         char[] password = this.generatePassword(seedSource).toCharArray();
         KeyStore keyStore = this.loadExistingKeyStore(keyStorePath, password);
         secretKey = this.retrieveSecretKey(keyStore, password);
      } catch (Exception var6) {
         this.handleException("Error in getting secret key", var6);
      }

      return secretKey;
   }

   private SecretKey retrieveSecretKey(KeyStore keyStore, char[] password) {
      SecretKey secretKey = null;

      try {
         SecretKeyEntry secretKeyEntry = (SecretKeyEntry)keyStore.getEntry("kerberos_secretkey", new PasswordProtection(password));
         secretKey = secretKeyEntry.getSecretKey();
      } catch (Exception var5) {
         this.handleException("Error in retrieving secret key from kerberos keystore", var5);
      }

      return secretKey;
   }

   private KeyStore loadExistingKeyStore(String keyStorePath, char[] password) {
      KeyStore keyStore = null;

      try {
         keyStore = KeyStore.getInstance("JCEKS");
         FileInputStream fis = new FileInputStream(keyStorePath);
         Throwable var5 = null;

         try {
            keyStore.load(fis, password);
         } catch (Throwable var15) {
            var5 = var15;
            throw var15;
         } finally {
            if (fis != null) {
               if (var5 != null) {
                  try {
                     fis.close();
                  } catch (Throwable var14) {
                     var5.addSuppressed(var14);
                  }
               } else {
                  fis.close();
               }
            }

         }
      } catch (Exception var17) {
         this.handleException("Error in loading kerberos keystore", var17);
      }

      return keyStore;
   }

   private void handleException(String caption, Exception exception) {
      String detail = String.format("%s: %s", caption, exception);
      throw new KerberosSecretDecryptorException(detail, exception);
   }
}
