package com.amazonaws.emr.node.provisioner.platform;

import com.amazonaws.emr.node.provisioner.platform.model.DoProvisionCheckinRequest;
import com.amazonaws.emr.node.provisioner.platform.model.GetConfigurationRequest;
import com.amazonaws.emr.node.provisioner.platform.model.GetConfigurationResponse;
import com.amazonaws.emr.node.provisioner.platform.model.ProvisionStatus;
import java.io.IOException;

public interface EmrPlatform {
   GetConfigurationResponse getConfiguration() throws IOException;

   GetConfigurationResponse getConfiguration(GetConfigurationRequest var1) throws IOException;

   void doProvisionCheckin(ProvisionStatus var1) throws IOException;

   void doProvisionCheckin(DoProvisionCheckinRequest var1) throws IOException;
}
