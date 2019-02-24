package com.amazonaws.emr.node.provisioner.bigtop.role;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.util.List;

public final class ComponentRoleFactory {
   private ComponentRoleFactory() {
   }

   public static List<ComponentRole> createManyFromYaml(String yaml) throws IOException {
      ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
      return (List)mapper.readValue(yaml, new TypeReference<List<ComponentRole>>() {
      });
   }
}
