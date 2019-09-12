package com.kencorhealth.campaign.dm.delivery.web;

import com.kencorhealth.campaign.dm.common.Identified;
import java.util.Map;

public class WorkflowData extends Identified {
    private boolean inUse;
    private String providerId;
    private String campaignId;
    private Map<String, Map<String, Object>> data;

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

    public Map<String, Map<String, Object>> getData() {
        return data;
    }

    public void setData(Map<String, Map<String, Object>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return
            "WorkflowData{" + "data=" + data + ", inUse=" + inUse +
            ", providerId=" + providerId + ", campaignId=" + campaignId +
            "}, " + super.toString();
    }
}
