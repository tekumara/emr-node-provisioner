package com.amazonaws.emr.node.provisioner.http;

import java.io.IOException;

public interface HttpClient {
   <T> T doRequest(Request var1, Class<T> var2) throws IOException;

   void doRequest(Request var1) throws IOException;
}
