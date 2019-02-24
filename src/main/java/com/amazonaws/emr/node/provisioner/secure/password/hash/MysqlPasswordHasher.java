package com.amazonaws.emr.node.provisioner.secure.password.hash;

import com.amazonaws.emr.node.provisioner.util.preconditions.CommonPreconditions;
import org.apache.commons.codec.digest.DigestUtils;

public final class MysqlPasswordHasher implements PasswordHasher {
   private MysqlPasswordHasher() {
   }

   public static MysqlPasswordHasher newInstance() {
      return new MysqlPasswordHasher();
   }

   public String hash(String password) {
      CommonPreconditions.require(password, "password");
      String sha1 = DigestUtils.sha1Hex(DigestUtils.sha1(password));
      return String.format("*%s", sha1.toUpperCase());
   }
}
