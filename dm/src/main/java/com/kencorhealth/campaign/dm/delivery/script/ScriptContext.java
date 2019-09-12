package com.kencorhealth.campaign.dm.delivery.script;

public class ScriptContext {
    private String campaignId;
    private String containerId;
    private String providerId;

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

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Override
    public String toString() {
        return
            "ScriptContext{" + "campaignId=" + campaignId + ", containerId=" +
            containerId + ", providerId=" + providerId + '}';
    }
}
