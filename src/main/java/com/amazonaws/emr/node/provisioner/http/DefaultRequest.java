package com.amazonaws.emr.node.provisioner.http;

import java.util.HashMap;
import java.util.Map;

public final class DefaultRequest implements Request {
   private HttpMethodName httpMethodName;
   private String path;
   private Map<String, String> queryParameters;

   public DefaultRequest(HttpMethodName httpMethodName, String path) {
      this.path = path;
      this.httpMethodName = httpMethodName;
      this.queryParameters = new HashMap();
   }

   public HttpMethodName getHttpMethodName() {
      return this.httpMethodName;
   }

   public String getPath() {
      return this.path;
   }

   public Request withQueryParameter(String name, String value) {
      this.addQueryParameter(name, value);
      return this;
   }

   public void addQueryParameter(String name, String value) {
      this.queryParameters.put(name, value);
   }

   public Request withQueryParameters(Map<String, String> queryParameters) {
      this.addQueryParameters(queryParameters);
      return this;
   }

   public void addQueryParameters(Map<String, String> queryParameters) {
      this.queryParameters.putAll(queryParameters);
   }

   public Map<String, String> getQueryParameters() {
      return this.queryParameters;
   }
}
