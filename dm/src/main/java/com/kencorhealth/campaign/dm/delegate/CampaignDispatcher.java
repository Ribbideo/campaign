package com.kencorhealth.campaign.dm.delegate;

import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.input.WorkflowDataInput;

public interface CampaignDispatcher {
    void dispatchExecCampaignSMS(CampaignSMS sms) throws CampaignException;
    void dispatchExecCampaignIVR(CampaignIVR ivr) throws CampaignException;
    void dispatchCampaignStageData(CampaignStageData csd)
        throws CampaignException;
    void dispatchWebDataCreation(WorkflowDataInput wdi)
        throws CampaignException;
}
