package com.kencorhealth.campaign.dm.input;

import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.Input;
import com.kencorhealth.campaign.dm.delivery.web.WorkflowData;

public class WorkflowDataInput extends Input<WorkflowData> {
    private String campaignId;
    private String providerId;
    private String id;

    public WorkflowDataInput() {
        id = CampaignUtil.uniqueString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }
    
    @Override
    public WorkflowData convert() {
        WorkflowData retVal = new WorkflowData();

        retVal.setId(id);
        retVal.autoFill();
        
        retVal.setProviderId(providerId);
        retVal.setCampaignId(campaignId);
        
        return retVal;
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
            "WorkflowDataInput{" + "providerId=" + providerId +
            ", campaignId=" + campaignId + "}, " + super.toString();
    }
}
