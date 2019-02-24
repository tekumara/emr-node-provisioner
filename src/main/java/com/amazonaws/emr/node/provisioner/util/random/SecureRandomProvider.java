package com.amazonaws.emr.node.provisioner.util.random;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Random;

public final class SecureRandomProvider implements RandomProvider {
   private SecureRandomProvider() {
   }

   public static SecureRandomProvider defaultProvider() {
      return new SecureRandomProvider();
   }

   public Random provide() {
      Random random = this.getSecureRandomInstance();
      this.forceRandomToSeedItselfSecurely(random);
      return random;
   }

   private Random getSecureRandomInstance() {
      try {
         return SecureRandom.getInstance("SHA1PRNG", "SUN");
      } catch (NoSuchProviderException | NoSuchAlgorithmException var2) {
         throw new RuntimeException("Failed providing a SecureRandom", var2);
      }
   }

   private void forceRandomToSeedItselfSecurely(Random random) {
      byte[] dummy = new byte[1];
      random.nextBytes(dummy);
   }
}
