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

}
