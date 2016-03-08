package ch.ebu.cpa.model;

import com.google.gson.annotations.SerializedName;

/**
 *  Copyright (c) European Broadcasting Union. All rights reserved.
 *
 *  Licence information is available from the LICENCE file.
 */
public class CpaClientIdentity {

    @SerializedName("client_id")
    private String clientId;

    @SerializedName("client_secret")
    private String clientSecret;

    @SerializedName("domain")
    private String domain;

    @SerializedName("grant_type")
    private String grantType;

    @SerializedName("device_code")
    private String deviceCode;

    public CpaClientIdentity() {
        super();
    }

    public CpaClientIdentity(String clientId, String clientSecret, String domain, String grantType, String deviceCode) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.domain = domain;
        this.grantType = grantType;
        this.deviceCode = deviceCode;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CpaClientIdentity{");
        sb.append("clientId='").append(clientId).append('\'');
        sb.append(", clientSecret='").append(clientSecret).append('\'');
        sb.append(", domain='").append(domain).append('\'');
        sb.append(", grantType='").append(grantType).append('\'');
        sb.append(", deviceCode='").append(deviceCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
