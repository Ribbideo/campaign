package com.kencorhealth.campaign.mq;

import com.kencorhealth.campaign.dm.delegate.CampaignForm;
import com.kencorhealth.campaign.dm.delegate.CampaignIVR;
import com.kencorhealth.campaign.dm.delegate.CampaignSMS;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.io.IOException;

class CampaignDispatcherImpl implements CampaignDispatcher, CMQConstants {
    @Override
    public void dispatchExecCampaignSMS(CampaignSMS sms)
        throws CampaignException, IOException {
        doSendMessage(sms, EXEC_CAMPAIGN_SMS);
    }
    
    @Override
    public void dispatchExecCampaignIVR(CampaignIVR ivr)
        throws CampaignException, IOException {
        doSendMessage(ivr, EXEC_CAMPAIGN_IVR);
    }
    
    @Override
    public void dispatchSubmitCampaignForm(CampaignForm cf)
        throws CampaignException, IOException {
        doSendMessage(cf, SUBMIT_CAMPAIGN_FORM);
    }
    
    private void doSendMessage(Object data, String topic)
        throws CampaignException, IOException {
        MQUtil.sendMessage(data, topic, MQFactory.get());
    }
}
