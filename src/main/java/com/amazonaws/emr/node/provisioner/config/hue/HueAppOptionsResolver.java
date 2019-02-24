package com.amazonaws.emr.node.provisioner.config.hue;

import java.util.List;

public interface HueAppOptionsResolver {
   HueAppOptions resolve(List<String> var1);
}
