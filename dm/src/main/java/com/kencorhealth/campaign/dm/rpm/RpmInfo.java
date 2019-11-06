package com.kencorhealth.campaign.dm.rpm;

import com.kencorhealth.campaign.dm.common.CampaignUtil;
import java.util.Map;

public class RpmInfo {
    private String baseUrl;
    private String authToken;
    private Map<String, Object> user;
    private ClinicInfo clinic;
    
    public String userId() {
        return (String) user.get("id");
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Map<String, Object> getUser() {
        return user;
    }

    public void setUser(Map<String, Object> user) {
        this.user = user;
    }

    public ClinicInfo getClinic() {
        return clinic;
    }

    public void setClinic(ClinicInfo clinic) {
        this.clinic = clinic;
    }
    
    @Override
    public String toString() {
        return
            "RpmInfo{" + "user=" + user + ", clinic=" + clinic +
            ", authToken=" + CampaignUtil.protect(authToken) +
            ", baseUrl=" + baseUrl + '}';
    }
}
