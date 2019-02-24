package com.amazonaws.emr.node.provisioner.platform.model.transform;

import com.amazonaws.emr.node.provisioner.http.DefaultRequest;
import com.amazonaws.emr.node.provisioner.http.HttpMethodName;
import com.amazonaws.emr.node.provisioner.http.Request;
import com.amazonaws.emr.node.provisioner.platform.model.GetConfigurationRequest;

public final class GetConfigurationRequestMarshaller implements RequestMarshaller<GetConfigurationRequest> {
   private static final String PATH = "/configuration";

   public GetConfigurationRequestMarshaller() {
   }

   public Request marshall(GetConfigurationRequest getConfigurationRequest) {
      return new DefaultRequest(HttpMethodName.GET, "/configuration");
   }
}
