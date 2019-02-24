package com.amazonaws.emr.node.provisioner.platform;

import com.amazonaws.emr.node.provisioner.http.HttpClient;
import com.amazonaws.emr.node.provisioner.http.JsonHttpClient;
import com.amazonaws.emr.node.provisioner.http.Request;
import com.amazonaws.emr.node.provisioner.platform.model.Configuration;
import com.amazonaws.emr.node.provisioner.platform.model.DoProvisionCheckinRequest;
import com.amazonaws.emr.node.provisioner.platform.model.GetConfigurationRequest;
import com.amazonaws.emr.node.provisioner.platform.model.GetConfigurationResponse;
import com.amazonaws.emr.node.provisioner.platform.model.ProvisionStatus;
import com.amazonaws.emr.node.provisioner.platform.model.transform.DoProvisionCheckinRequestMarshaller;
import com.amazonaws.emr.node.provisioner.platform.model.transform.GetConfigurationRequestMarshaller;
import com.amazonaws.emr.node.provisioner.secure.kerberos.KerberosSecretDecryptor;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;

public final class EmrPlatformClient implements EmrPlatform {
   public static final List<String> KERBEROS_SECRET_TYPES = ImmutableList.of("admin.password", "cross.realm.trust.principal.password", "ad.domain.join.password", "ad.domain.join.user");
   private static final String DEFAULT_ENDPOINT = "http://localhost:8321";
   private static final String KERBEROS_CLASSIFICATION = "kerberos";
   private final HttpClient client;

   public EmrPlatformClient() {
      this("http://localhost:8321");
   }

   public EmrPlatformClient(String endpoint) {
      this.client = new JsonHttpClient(endpoint);
   }

   public GetConfigurationResponse getConfiguration() throws IOException {
      return this.getConfiguration(new GetConfigurationRequest());
   }

   public GetConfigurationResponse getConfiguration(GetConfigurationRequest getConfigurationRequest) throws IOException {
      Request request = (new GetConfigurationRequestMarshaller()).marshall(getConfigurationRequest);
      GetConfigurationResponse getConfigurationResponse = (GetConfigurationResponse)this.client.doRequest(request, GetConfigurationResponse.class);
      this.decryptKerberosSecretsIfPresent(getConfigurationResponse);
      return getConfigurationResponse;
   }

   public void doProvisionCheckin(ProvisionStatus provisionStatus) throws IOException {
      this.doProvisionCheckin(new DoProvisionCheckinRequest(provisionStatus));
   }

   public void doProvisionCheckin(DoProvisionCheckinRequest doProvisionCheckinRequest) throws IOException {
      Request request = (new DoProvisionCheckinRequestMarshaller()).marshall(doProvisionCheckinRequest);
      this.client.doRequest(request);
   }

   private void decryptKerberosSecretsIfPresent(GetConfigurationResponse getConfigurationResponse) {
      Iterator var2 = getConfigurationResponse.getConfigurations().iterator();

      while(var2.hasNext()) {
         Configuration configuration = (Configuration)var2.next();
         if (StringUtils.equals(configuration.getClassification(), "kerberos")) {
            this.decryptKerberosSecrets(configuration, getConfigurationResponse.getInstanceId());
            break;
         }
      }

   }

   private void decryptKerberosSecrets(Configuration configuration, String instanceId) {
      KerberosSecretDecryptor kerberosSecretDecryptor = new KerberosSecretDecryptor(instanceId);
      Iterator var4 = configuration.getProperties().entrySet().iterator();

      while(var4.hasNext()) {
         Entry<String, String> entry = (Entry)var4.next();
         if (KERBEROS_SECRET_TYPES.contains(entry.getKey())) {
            entry.setValue(kerberosSecretDecryptor.decryptText((String)entry.getValue()));
         }
      }

   }
}
