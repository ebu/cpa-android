package ch.ebu.cpa.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by npietri on 29.04.15.
 */
public class CpaUserToken extends CpaClientToken {

    @SerializedName("user_name")
    private String userName;

    public CpaUserToken() {
    }

    public CpaUserToken(String accessToken, String domain, String domainDisplayName, String expiresIn, String tokenType, String userName) {
        super(accessToken, domain, domainDisplayName, expiresIn, tokenType);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CpaUserToken{");
        sb.append("accessToken='").append(getAccessToken()).append('\'');
        sb.append(", tokenType='").append(getTokenType()).append('\'');
        sb.append(", expiresIn='").append(getExpiresIn()).append('\'');
        sb.append(", domain='").append(getDomain()).append('\'');
        sb.append(", domainDisplayName='").append(getDomainDisplayName()).append('\'');
        sb.append("userName='").append(userName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
