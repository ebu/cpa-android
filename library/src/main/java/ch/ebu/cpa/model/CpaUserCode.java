package ch.ebu.cpa.model;

import com.google.gson.annotations.SerializedName;

/**
 *  Copyright (c) European Broadcasting Union. All rights reserved.
 *
 *  Licence information is available from the LICENCE file.
 */
public class CpaUserCode {

    @SerializedName(value = "device_code")
    private String deviceCode;

    @SerializedName(value = "user_code")
    private String userCode;

    @SerializedName(value = "verification_uri")
    private String verificationUri;

    @SerializedName(value = "interval")
    private String interval;

    @SerializedName(value = "expires_in")
    private String expiresIn;

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getVerificationUri() {
        return verificationUri;
    }

    public void setVerificationUri(String verificationUri) {
        this.verificationUri = verificationUri;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CpaUserCode{");
        sb.append("deviceCode='").append(deviceCode).append('\'');
        sb.append(", userCode='").append(userCode).append('\'');
        sb.append(", verificationUri='").append(verificationUri).append('\'');
        sb.append(", interval='").append(interval).append('\'');
        sb.append(", expiresIn='").append(expiresIn).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
