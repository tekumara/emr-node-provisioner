package com.amazonaws.emr.node.provisioner.bigtop.config;

import com.amazonaws.emr.node.provisioner.bigtop.config.override.node.NodeTypeConfigAppliers;
import com.amazonaws.emr.node.provisioner.bigtop.role.RoleTranslators;
import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigApplier;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.config.cluster.ClusterInfoConfigApplier;
import com.amazonaws.emr.node.provisioner.config.hbase.HbaseConfigApplier;
import com.amazonaws.emr.node.provisioner.config.hdfs.HdfsConfigApplier;
import com.amazonaws.emr.node.provisioner.config.hue.HueAppConfigApplier;
import com.amazonaws.emr.node.provisioner.config.hue.HueAppOptions;
import com.amazonaws.emr.node.provisioner.config.hue.HueAppOptionsResolvers;
import com.amazonaws.emr.node.provisioner.config.mapping.ConfigMapper;
import com.amazonaws.emr.node.provisioner.config.mapping.ConfigMappingGroup;
import com.amazonaws.emr.node.provisioner.config.mapping.ConfigMappingGroupFactory;
import com.amazonaws.emr.node.provisioner.config.mapping.operators.DnsReverseLookupMapOperator;
import com.amazonaws.emr.node.provisioner.config.mapping.operators.MapOperatorProvider;
import com.amazonaws.emr.node.provisioner.config.mapping.operators.MapOperatorProviders;
import com.amazonaws.emr.node.provisioner.config.spark.SparkConfigAppliers;
import com.amazonaws.emr.node.provisioner.dns.DnsResolver;
import com.amazonaws.emr.node.provisioner.util.StringUtil;
import com.amazonaws.emr.node.provisioner.util.filter.ListFilter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PlatformConfigApplier implements ConfigApplier {
   private static final Logger logger = LoggerFactory.getLogger(PlatformConfigApplier.class);
   private static final String COMPONENT_OVERRIDES_RESOURCE = "/component-overrides.yaml";
   private static final String APP_CONFIG_MAPPINGS_RESOURCE = "/app-config-mappings.yaml";
   private final PlatformContext context;
   private final List<String> roles;
   private final ConfigApplier clusterInfoApplier;
   private final ConfigApplier nodeTypeConfigApplier;
   private final ConfigApplier componentConfigApplier;
   private final ConfigApplier hdfsConfigApplier;
   private final ConfigApplier sparkConfigApplier;
   private final ConfigApplier hueConfigApplier;
   private final ConfigApplier hbaseConfigApplier;
   private final ConfigMapper appConfigMapper;

   public PlatformConfigApplier(PlatformContext context, DnsResolver dnsResolver, ListFilter<String> componentFilter) {
      this.context = context;
      this.roles = buildRoles(context.getComponentNames(), componentFilter);
      this.clusterInfoApplier = buildClusterInfoApplier(context);
      this.nodeTypeConfigApplier = NodeTypeConfigAppliers.defaultApplier(context.getNodeType());
      this.componentConfigApplier = buildComponentConfigApplier(context.getComponentNames());
      this.hdfsConfigApplier = buildHdfsConfigApplier(context);
      this.sparkConfigApplier = buildSparkConfigApplier(context);
      this.hueConfigApplier = buildHueConfigApplier(context.getComponentNames());
      this.hbaseConfigApplier = buildHbaseConfigApplier(context);
      this.appConfigMapper = buildAppConfigMapper(dnsResolver);
   }

   public void applyTo(Config config) throws ConfigException {
      this.clusterInfoApplier.applyTo(config);
      this.nodeTypeConfigApplier.applyTo(config);
      this.componentConfigApplier.applyTo(config);
      config.put(BigtopConfigVar.ROLES_ENABLED.getKey(), true);
      config.put((String)BigtopConfigVar.ROLES.getKey(), (Iterable)this.roles);
      this.hdfsConfigApplier.applyTo(config);
      this.sparkConfigApplier.applyTo(config);
      this.hueConfigApplier.applyTo(config);
      this.appConfigMapper.map(this.context.getAppConfig(), config);
      this.hbaseConfigApplier.applyTo(config);
   }

   private static List<String> buildRoles(List<String> componentNames, ListFilter<String> componentFilter) {
      List<String> filteredComponentNames = componentFilter.filter(componentNames);
      logger.info("Filtered component names:\n{}", StringUtil.indent(filteredComponentNames.toString()));
      return RoleTranslators.defaultTranslator().translate((Iterable)filteredComponentNames);
   }

   private static ConfigApplier buildClusterInfoApplier(PlatformContext context) {
      return ClusterInfoConfigApplier.builder().withPlatformContext(context).build();
   }

   private static ConfigApplier buildComponentConfigApplier(List<String> components) {
      List<ComponentOverride> defaults = ComponentOverrideFactory.createManyFromYamlResource("/component-overrides.yaml");
      ComponentConfigApplierFactory factory = new ComponentConfigApplierFactory(defaults);
      return factory.create(components);
   }

   private static ConfigApplier buildHdfsConfigApplier(PlatformContext context) {
      return HdfsConfigApplier.builder().withAppConfig(context.getAppConfig()).build();
   }

   private static ConfigApplier buildSparkConfigApplier(PlatformContext context) {
      return SparkConfigAppliers.getSparkConfigApplierForConfiguration(context.getComponentNames(), context.getAppConfig(), context.getJobFlowInfo());
   }

   private static ConfigApplier buildHueConfigApplier(List<String> componentNames) {
      HueAppOptions hueAppOptions = HueAppOptionsResolvers.defaultResolver().resolve(componentNames);
      return HueAppConfigApplier.using(hueAppOptions);
   }

   private static ConfigApplier buildHbaseConfigApplier(PlatformContext context) {
      return HbaseConfigApplier.builder().withAppConfig(context.getAppConfig()).withClusterId(context.getClusterId()).build();
   }

   private static ConfigMapper buildAppConfigMapper(DnsResolver dnsResolver) {
      List<ConfigMappingGroup> groups = ConfigMappingGroupFactory.createManyFromYamlResource("/app-config-mappings.yaml");
      MapOperatorProvider provider = MapOperatorProviders.defaultProviderWithOverrides(new DnsReverseLookupMapOperator(dnsResolver));
      return new ConfigMapper(groups, provider);
   }
}
