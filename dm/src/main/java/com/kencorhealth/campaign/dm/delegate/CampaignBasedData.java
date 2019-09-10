package com.kencorhealth.campaign.dm.delegate;

public class CampaignBasedData {
    private String providerId;
    private String campaignId;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    @Override
    public String toString() {
        return
            "CampaignBasedData{" + "campaignId=" + campaignId +
            ", providerId=" + providerId + '}';
    }
}
