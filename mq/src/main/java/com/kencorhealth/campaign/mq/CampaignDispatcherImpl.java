package com.kencorhealth.campaign.mq;

import com.kencorhealth.campaign.dm.delegate.CampaignDispatcher;
import com.kencorhealth.campaign.dm.delegate.CampaignStageData;
import com.kencorhealth.campaign.dm.delegate.CampaignIVR;
import com.kencorhealth.campaign.dm.delegate.CampaignSMS;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.input.WorkflowDataInput;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

class CampaignDispatcherImpl implements CampaignDispatcher, CMQConstants {
    @Override
    public void dispatchExecCampaignSMS(CampaignSMS sms)
        throws CampaignException {
        doSendMessage(sms, EXEC_CAMPAIGN_SMS);
    }
    
    @Override
    public void dispatchExecCampaignIVR(CampaignIVR ivr)
        throws CampaignException {
        doSendMessage(ivr, EXEC_CAMPAIGN_IVR);
    }
    
    @Override
    public void dispatchCampaignStageData(CampaignStageData csd)
        throws CampaignException {
        doSendMessage(csd, SUBMIT_CAMPAIGN_STAGE_DATA);
    }

    @Override
    public void dispatchWebDataCreation(WorkflowDataInput wdi)
        throws CampaignException {
        doSendMessage(wdi, SUBMIT_WEB_DATA_CREATE);
    }
    
    private void doSendMessage(Object data, String topic)
        throws CampaignException {
        try {
            MQUtil.sendMessage(data, topic, MQFactory.get());
        } catch (TimeoutException | IOException ex) {
            throw new CampaignException(ex);
        }
    }
}
