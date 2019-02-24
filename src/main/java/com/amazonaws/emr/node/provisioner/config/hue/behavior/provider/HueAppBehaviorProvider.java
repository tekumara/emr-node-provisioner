package com.amazonaws.emr.node.provisioner.config.hue.behavior.provider;

import com.amazonaws.emr.node.provisioner.config.hue.behavior.HueAppBehavior;
import java.util.List;

public interface HueAppBehaviorProvider {
   List<HueAppBehavior> provide();
}
