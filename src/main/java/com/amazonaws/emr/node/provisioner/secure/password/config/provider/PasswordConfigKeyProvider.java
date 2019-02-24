package com.amazonaws.emr.node.provisioner.secure.password.config.provider;

import com.amazonaws.emr.node.provisioner.secure.password.config.PasswordConfigKey;
import java.util.List;

public interface PasswordConfigKeyProvider {
   List<PasswordConfigKey> provide();
}
