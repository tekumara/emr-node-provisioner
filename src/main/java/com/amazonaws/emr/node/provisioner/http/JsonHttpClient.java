package com.amazonaws.emr.node.provisioner.http;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpBackOffIOExceptionHandler;
import com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandler;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.BackOff;
import com.google.api.client.util.ExponentialBackOff.Builder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public final class JsonHttpClient implements HttpClient {
   private static final int DEFAULT_MAX_ELAPSE_TIME_MILLIS;
   private static final HttpTransport HTTP_TRANSPORT;
   private static final JsonFactory JSON_FACTORY;
   private final String endpoint;
   private final HttpRequestFactory requestFactory;

   public JsonHttpClient(String endpoint) {
      this.endpoint = endpoint;
      this.requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
         public void initialize(HttpRequest httpRequest) throws IOException {
            httpRequest.setParser(new JsonObjectParser(JsonHttpClient.JSON_FACTORY));
            BackOff backOff = (new Builder()).setMaxElapsedTimeMillis(JsonHttpClient.DEFAULT_MAX_ELAPSE_TIME_MILLIS).build();
            httpRequest.setUnsuccessfulResponseHandler(new HttpBackOffUnsuccessfulResponseHandler(backOff));
            httpRequest.setIOExceptionHandler(new HttpBackOffIOExceptionHandler(backOff));
         }
      });
   }

   public <T> T doRequest(Request request, Class<T> responseClass) throws IOException {
      HttpRequest httpRequest = this.buildHttpRequest(request);
      HttpResponse httpResponse = httpRequest.execute();
      return httpResponse.parseAs(responseClass);
   }

   public void doRequest(Request request) throws IOException {
      HttpRequest httpRequest = this.buildHttpRequest(request);
      HttpResponse httpResponse = httpRequest.execute();
      if (httpResponse.getStatusCode() != 200) {
         throw new IOException(httpRequest.getUrl() + " returned status code: " + httpResponse.getStatusCode());
      }
   }

   private HttpRequest buildHttpRequest(Request request) throws IOException {
      GenericUrl url = this.buildUrl(request);
      switch(request.getHttpMethodName()) {
      case GET:
         return this.requestFactory.buildGetRequest(url);
      case POST:
         return this.requestFactory.buildPostRequest(url, (HttpContent)null);
      default:
         throw new IllegalArgumentException("Unsupported http method name " + request.getHttpMethodName());
      }
   }

   private GenericUrl buildUrl(Request request) {
      GenericUrl url = new GenericUrl(this.endpoint);
      url.setRawPath(request.getPath());
      url.putAll(request.getQueryParameters());
      return url;
   }

   static {
      DEFAULT_MAX_ELAPSE_TIME_MILLIS = (new Long(TimeUnit.SECONDS.toMillis(15L))).intValue();
      HTTP_TRANSPORT = new NetHttpTransport();
      JSON_FACTORY = new JacksonFactory();
   }
}
