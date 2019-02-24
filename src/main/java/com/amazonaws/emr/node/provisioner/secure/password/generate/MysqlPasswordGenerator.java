package com.amazonaws.emr.node.provisioner.secure.password.generate;

import com.amazonaws.emr.node.provisioner.util.preconditions.CommonPreconditions;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.ArrayUtils;

public final class MysqlPasswordGenerator implements PasswordGenerator {
   private static final List<Character> SYMBOLS;
   private static final int PASSWORD_LENGTH = 16;
   private final Random random;

   private MysqlPasswordGenerator(Random random) {
      this.random = random;
   }

   public static MysqlPasswordGenerator newInstance(Random random) {
      CommonPreconditions.require(random, "random");
      return new MysqlPasswordGenerator(random);
   }

   public String generate() {
      StringBuilder passwordBuilder = new StringBuilder();

      for(int i = 0; i < 16; ++i) {
         Character randomSymbol = (Character)SYMBOLS.get(this.random.nextInt(SYMBOLS.size()));
         passwordBuilder.append(randomSymbol);
      }

      return passwordBuilder.toString();
   }

   static {
      String lowerCaseLetters = "abcdefgijkmnopqrstwxyz";
      String upperCaseLetters = "ABCDEFGHJKLMNPQRSTWXYZ";
      String numbers = "123456789";
      SYMBOLS = ImmutableList.builder().add(ArrayUtils.toObject(lowerCaseLetters.toCharArray())).add(ArrayUtils.toObject(upperCaseLetters.toCharArray())).add(ArrayUtils.toObject(numbers.toCharArray())).build();
   }
}
