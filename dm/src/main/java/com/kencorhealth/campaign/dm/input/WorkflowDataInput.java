package com.kencorhealth.campaign.dm.input;

import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.Input;
import com.kencorhealth.campaign.dm.delivery.web.WorkflowData;

public class WorkflowDataInput extends Input<WorkflowData> {
    private String campaignId;
    private String clinicId;
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
        
        retVal.setClinicId(clinicId);
        retVal.setCampaignId(campaignId);
        
        return retVal;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    @Override
    public String toString() {
        return
            "WorkflowDataInput{" + "clinicId=" + clinicId +
            ", campaignId=" + campaignId + "}, " + super.toString();
    }
}
