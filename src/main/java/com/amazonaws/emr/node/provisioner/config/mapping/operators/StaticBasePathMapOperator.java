package com.amazonaws.emr.node.provisioner.config.mapping.operators;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.config.mapping.ConfigMapping;
import com.amazonaws.emr.node.provisioner.config.mapping.ConfigMappingType;
import com.amazonaws.emr.node.provisioner.io.FileAncestorIterator;
import com.amazonaws.emr.node.provisioner.util.FileUtil;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.Iterator;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class StaticBasePathMapOperator implements MapOperator {
   private static final Logger logger = LoggerFactory.getLogger(StaticBasePathMapOperator.class);
   private static final Pattern variablePattern = Pattern.compile("\\$\\{[^\\}\\$ ]+\\}");

   public StaticBasePathMapOperator() {
   }

   public void map(ConfigMapping mapping, Config source, Config destination) throws ConfigException {
      String path = source.getString(mapping.getSourcePath());
      File file = new File(path);
      if (!file.isAbsolute()) {
         logger.debug("Mapping {} of relative path '{}' is not applicable.", mapping, path);
      } else {
         File baseFile = this.getStaticBaseFile(file);
         if (baseFile != null && !FileUtil.isRoot(baseFile)) {
            destination.put(mapping.getDestinationPath(), baseFile.getPath());
         } else {
            logger.debug("Mapping {} of path '{}' is not applicable.", mapping, path);
         }
      }
   }

   public ConfigMappingType getType() {
      return ConfigMappingType.STATIC_BASE_PATH;
   }

   private File getStaticBaseFile(File file) {
      Iterator var2 = Lists.reverse(Lists.newArrayList(new FileAncestorIterator(file))).iterator();

      File ancestor;
      do {
         if (!var2.hasNext()) {
            return file;
         }

         ancestor = (File)var2.next();
      } while(!this.containsSubstitutionVariable(ancestor.getName()));

      return ancestor.getParentFile();
   }

   private boolean containsSubstitutionVariable(String filename) {
      return variablePattern.matcher(filename).find();
   }
}
