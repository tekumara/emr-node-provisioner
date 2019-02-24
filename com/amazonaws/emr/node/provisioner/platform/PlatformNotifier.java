package com.amazonaws.emr.node.provisioner.platform;

import com.amazonaws.emr.node.provisioner.config.ConfigException;
import com.amazonaws.emr.node.provisioner.platform.model.DoProvisionCheckinRequest;
import com.amazonaws.emr.node.provisioner.platform.model.ProvisionFailureReason;
import com.amazonaws.emr.node.provisioner.platform.model.ProvisionStatus;
import com.amazonaws.emr.node.provisioner.puppet.api.PuppetException;
import java.io.IOException;

public final class PlatformNotifier {
   private final EmrPlatform platform;

   public PlatformNotifier() {
      this(new EmrPlatformClient());
   }

   public PlatformNotifier(EmrPlatform platform) {
      this.platform = platform;
   }

   public void notifySuccess() throws IOException {
      this.platform.doProvisionCheckin(ProvisionStatus.SUCCESSFUL);
   }

   public void notifyFailure(Exception exception) throws IOException {
      this.notifyFailure(exception.getMessage(), exception);
   }

   public void notifyFailure(String message, Exception exception) throws IOException {
      DoProvisionCheckinRequest request = (new DoProvisionCheckinRequest(ProvisionStatus.FAILED)).withProvisionFailureReason(this.convertToFailureReason(exception)).withMessage(message);
      this.platform.doProvisionCheckin(request);
   }

   private ProvisionFailureReason convertToFailureReason(Exception exception) {
      if (exception instanceof ConfigException) {
         return ProvisionFailureReason.CONFIG_ERROR;
      } else if (exception instanceof PuppetException) {
         return ProvisionFailureReason.DEPLOYMENT_ERROR;
      } else {
         return exception instanceof IOException ? ProvisionFailureReason.INTERNAL_ERROR : ProvisionFailureReason.UNKNOWN_ERROR;
      }
   }
}
