package com.kencorhealth.campaign.dm.rpm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "activationCode", "chatURL", "phoneNumber" })
public class RouterInfo {
    private String apiPath;
    private String baseURL;

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String rpmUrl() {
        return baseURL + apiPath;
    }

    @Override
    public String toString() {
        return
            "RouterInfo{" + "apiPath=" + apiPath + ", baseURL=" + baseURL + '}';
    }
}
