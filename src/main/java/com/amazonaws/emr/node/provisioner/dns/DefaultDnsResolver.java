package com.amazonaws.emr.node.provisioner.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class DefaultDnsResolver implements DnsResolver {
   public DefaultDnsResolver() {
   }

   public String reverseLookup(String host) {
      try {
         return InetAddress.getByName(host).getCanonicalHostName();
      } catch (UnknownHostException var3) {
         throw new RuntimeException("Failed reverse lookup of " + host, var3);
      }
   }
}
