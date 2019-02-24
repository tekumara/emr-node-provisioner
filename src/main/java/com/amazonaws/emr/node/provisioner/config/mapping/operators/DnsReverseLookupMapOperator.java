package com.amazonaws.emr.node.provisioner.config.mapping.operators;

import com.amazonaws.emr.node.provisioner.config.Config;
import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.config.mapping.ConfigMapping;
import com.amazonaws.emr.node.provisioner.config.mapping.ConfigMappingType;
import com.amazonaws.emr.node.provisioner.dns.DefaultDnsResolver;
import com.amazonaws.emr.node.provisioner.dns.DnsResolver;

public final class DnsReverseLookupMapOperator implements MapOperator {
   private final DnsResolver resolver;

   public DnsReverseLookupMapOperator() {
      this.resolver = new DefaultDnsResolver();
   }

   public DnsReverseLookupMapOperator(DnsResolver resolver) {
      this.resolver = resolver;
   }

   public void map(ConfigMapping mapping, Config source, Config destination) throws ConfigException {
      String string = source.getString(mapping.getSourcePath());
      String resolvedHostname = this.resolver.reverseLookup(string);
      destination.put(mapping.getDestinationPath(), resolvedHostname);
   }

   public ConfigMappingType getType() {
      return ConfigMappingType.DNS_REVERSE_LOOKUP;
   }
}
