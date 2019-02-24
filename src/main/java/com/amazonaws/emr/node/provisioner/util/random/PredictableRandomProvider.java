package com.amazonaws.emr.node.provisioner.util.random;

import java.util.Random;

public final class PredictableRandomProvider implements RandomProvider {
   private static final long SEED = 42L;

   private PredictableRandomProvider() {
   }

   public static PredictableRandomProvider defaultProvider() {
      return new PredictableRandomProvider();
   }

   public Random provide() {
      return new Random(42L);
   }
}
