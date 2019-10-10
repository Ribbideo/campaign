package com.kencorhealth.campaign.dm.delivery.web;

import com.kencorhealth.campaign.dm.common.Identified;
import java.util.List;

public class WorkflowData extends Identified {
    private boolean inUse;
    private String providerId;
    private String campaignId;
    private List<FormData> formData;

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }
    
    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public List<FormData> getFormData() {
        return formData;
    }

    public void setFormData(List<FormData> formData) {
        this.formData = formData;
    }

    @Override
    public String toString() {
        return
            "WorkflowData{" + "formData=" + formData + ", inUse=" + inUse +
            ", providerId=" + providerId + ", campaignId=" + campaignId +
            "}, " + super.toString();
    }
}
