package com.kencorhealth.campaign.dm.delivery.script;

import com.kencorhealth.campaign.dm.auth.AuthToken;

public class ScriptContext {
    private String campaignId;
    private String containerId;
    private AuthToken authToken;

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return
            "ScriptContext{" + "campaignId=" + campaignId + ", containerId=" +
            containerId + ", authToken=" + authToken + '}';
    }
}
