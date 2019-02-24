package com.amazonaws.emr.node.provisioner.config.mapping.operators;

import com.amazonaws.emr.node.provisioner.config.mapping.ConfigMappingType;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class MapOperatorProvider {
   private final Map<ConfigMappingType, MapOperator> operatorMap;

   private MapOperatorProvider(MapOperatorProvider.Builder builder) {
      this.operatorMap = ImmutableMap.copyOf(builder.operatorMap);
   }

   public MapOperator provide(ConfigMappingType type) {
      return (MapOperator)this.operatorMap.get(type);
   }

   public static final class Builder {
      private final Map<ConfigMappingType, MapOperator> operatorMap = new HashMap();

      public Builder() {
      }

      public MapOperatorProvider.Builder register(MapOperator... operators) {
         MapOperator[] var2 = operators;
         int var3 = operators.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MapOperator operator = var2[var4];
            this.operatorMap.put(operator.getType(), operator);
         }

         return this;
      }

      public MapOperatorProvider build() {
         this.validateOperatorMap();
         return new MapOperatorProvider(this);
      }

      private void validateOperatorMap() {
         Set<ConfigMappingType> typeSet = ImmutableSet.copyOf(ConfigMappingType.values());
         Preconditions.checkArgument(this.operatorMap.keySet().equals(typeSet), "Missing map operators for following mapping types: " + Sets.difference(this.operatorMap.keySet(), typeSet));
      }
   }
}
