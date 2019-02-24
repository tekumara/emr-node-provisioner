package com.amazonaws.emr.node.provisioner.bigtop.config;

import com.amazonaws.emr.node.provisioner.dns.DefaultDnsResolver;
import com.amazonaws.emr.node.provisioner.dns.DnsResolver;
import com.amazonaws.emr.node.provisioner.util.filter.ListFilter;
import com.amazonaws.emr.node.provisioner.util.filter.PassThroughListFilter;
import java.io.IOException;

public final class PlatformConfigApplierFactory {
   private final DnsResolver resolver;
   private final ListFilter<String> componentFilter;

   private PlatformConfigApplierFactory(PlatformConfigApplierFactory.Builder builder) {
      this.resolver = builder.resolver;
      this.componentFilter = builder.componentFilter;
   }

   public PlatformConfigApplier create(PlatformContext platformContext) throws IOException {
      return new PlatformConfigApplier(platformContext, this.resolver, this.componentFilter);
   }

   public static PlatformConfigApplierFactory defaultFactory() {
      return builder().build();
   }

   public static PlatformConfigApplierFactory.Builder builder() {
      return new PlatformConfigApplierFactory.Builder();
   }

   public static final class Builder {
      private DnsResolver resolver = new DefaultDnsResolver();
      private ListFilter<String> componentFilter = new PassThroughListFilter();

      public Builder() {
      }

      public PlatformConfigApplierFactory.Builder withResolver(DnsResolver resolver) {
         this.resolver = resolver;
         return this;
      }

      public PlatformConfigApplierFactory.Builder withComponentFilter(ListFilter<String> componentFilter) {
         this.componentFilter = componentFilter;
         return this;
      }

      public PlatformConfigApplierFactory build() {
         return new PlatformConfigApplierFactory(this);
      }
   }
}
