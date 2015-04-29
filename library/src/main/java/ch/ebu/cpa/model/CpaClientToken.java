package ch.ebu.cpa.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by npietri on 29.04.15.
 */
public class CpaClientToken {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private String expiresIn;

    @SerializedName("domain")
    private String domain;

    @SerializedName("domain_display_name")
    private String domainDisplayName;

    public CpaClientToken() {
    }

    public CpaClientToken(String accessToken, String domain, String domainDisplayName, String expiresIn, String tokenType) {
        this.accessToken = accessToken;
        this.domain = domain;
        this.domainDisplayName = domainDisplayName;
        this.expiresIn = expiresIn;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomainDisplayName() {
        return domainDisplayName;
    }

    public void setDomainDisplayName(String domainDisplayName) {
        this.domainDisplayName = domainDisplayName;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CpaClientToken{");
        sb.append("accessToken='").append(accessToken).append('\'');
        sb.append(", tokenType='").append(tokenType).append('\'');
        sb.append(", expiresIn='").append(expiresIn).append('\'');
        sb.append(", domain='").append(domain).append('\'');
        sb.append(", domainDisplayName='").append(domainDisplayName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
