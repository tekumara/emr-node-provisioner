package com.amazonaws.emr.node.provisioner.http;

import java.util.Map;

public interface Request {
   HttpMethodName getHttpMethodName();

   String getPath();

   Request withQueryParameter(String var1, String var2);

   void addQueryParameter(String var1, String var2);

   Request withQueryParameters(Map<String, String> var1);

   void addQueryParameters(Map<String, String> var1);

   Map<String, String> getQueryParameters();
}
