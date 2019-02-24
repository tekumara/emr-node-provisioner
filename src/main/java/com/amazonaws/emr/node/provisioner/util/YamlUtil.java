package com.amazonaws.emr.node.provisioner.util;

import com.amazonaws.emr.node.provisioner.config.Config;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

public final class YamlUtil {
   public YamlUtil() {
   }

   public static String dump(Object object, YamlUtil.ScalarStyle scalarStyle) {
      Yaml yaml = buildYaml(scalarStyle);
      return yaml.dump(object);
   }

   private static Yaml buildYaml(YamlUtil.ScalarStyle scalarStyle) {
      DumperOptions options = new DumperOptions();
      options.setExplicitStart(true);
      options.setDefaultFlowStyle(FlowStyle.BLOCK);
      switch(scalarStyle) {
      case SINGLE_QUOTED:
         options.setDefaultScalarStyle(org.yaml.snakeyaml.DumperOptions.ScalarStyle.SINGLE_QUOTED);
         break;
      default:
         options.setDefaultScalarStyle(org.yaml.snakeyaml.DumperOptions.ScalarStyle.PLAIN);
      }

      return new Yaml(new YamlUtil.CustomRepresenter(), options);
   }

   private static final class CustomRepresenter extends Representer {
      public CustomRepresenter() {
         this.representers.put(Config.class, new YamlUtil.CustomRepresenter.RepresentConfig());
      }

      private final class RepresentConfig implements Represent {
         private RepresentConfig() {
         }

         public Node representData(Object data) {
            Config config = (Config)data;
            return CustomRepresenter.this.representMapping(Tag.MAP, config.unwrapped(), FlowStyle.BLOCK.getStyleBoolean());
         }
      }
   }

   public static enum ScalarStyle {
      PLAIN,
      SINGLE_QUOTED;

      private ScalarStyle() {
      }
   }
}
