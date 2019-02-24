package com.amazonaws.emr.node.provisioner.platform.model.transform;

import com.amazonaws.emr.node.provisioner.http.DefaultRequest;
import com.amazonaws.emr.node.provisioner.http.HttpMethodName;
import com.amazonaws.emr.node.provisioner.http.Request;
import com.amazonaws.emr.node.provisioner.platform.model.DoProvisionCheckinRequest;

public final class DoProvisionCheckinRequestMarshaller implements RequestMarshaller<DoProvisionCheckinRequest> {
   private static final String STATUS_PARAM = "status";
   private static final String MESSAGE_PARAM = "message";
   private static final String FAILURE_REASON_PARAM = "failureReason";
   private static final String PATH = "/provisionCheckin";

   public DoProvisionCheckinRequestMarshaller() {
   }

   public Request marshall(DoProvisionCheckinRequest doProvisionCheckinRequest) {
      Request request = (new DefaultRequest(HttpMethodName.POST, "/provisionCheckin")).withQueryParameter("status", doProvisionCheckinRequest.getProvisionStatus().name());
      if (doProvisionCheckinRequest.getMessage() != null) {
         request.addQueryParameter("message", doProvisionCheckinRequest.getMessage());
      }

      if (doProvisionCheckinRequest.getProvisionFailureReason() != null) {
         request.addQueryParameter("failureReason", doProvisionCheckinRequest.getProvisionFailureReason().name());
      }

      return request;
   }
}
