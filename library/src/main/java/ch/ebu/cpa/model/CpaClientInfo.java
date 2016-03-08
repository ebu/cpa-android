package ch.ebu.cpa.model;

import com.google.gson.annotations.SerializedName;

/**
 *  Copyright (c) European Broadcasting Union. All rights reserved.
 *
 *  Licence information is available from the LICENCE file.
 */
public class CpaClientInfo {

    @SerializedName(value = "client_name")
    private String clientName;

    @SerializedName(value = "software_id")
    private String softwareId;

    @SerializedName(value = "software_version")
    private String softwareVersion;

    public CpaClientInfo() {
        super();
    }

    public CpaClientInfo(String clientName, String softwareId, String softwareVersion) {
        this.clientName = clientName;
        this.softwareId = softwareId;
        this.softwareVersion = softwareVersion;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CpaClientInfo{");
        sb.append("clientName='").append(clientName).append('\'');
        sb.append(", softwareId='").append(softwareId).append('\'');
        sb.append(", softwareVersion='").append(softwareVersion).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
