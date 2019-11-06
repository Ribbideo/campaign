package com.kencorhealth.campaign.dm.delegate;

public class CampaignBasedData {
    private String clinicId;
    private String campaignId;

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
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
            ", clinicId=" + clinicId + '}';
    }
}
