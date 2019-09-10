package com.kencorhealth.campaign.mq;

import com.kencorhealth.campaign.dm.delegate.CampaignForm;
import com.kencorhealth.campaign.dm.delegate.CampaignIVR;
import com.kencorhealth.campaign.dm.delegate.CampaignSMS;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.io.IOException;

public interface CampaignDispatcher {
    void dispatchExecCampaignSMS(CampaignSMS sms) throws CampaignException, IOException;
    void dispatchExecCampaignIVR(CampaignIVR ivr) throws CampaignException, IOException;
    void dispatchSubmitCampaignForm(CampaignForm cf) throws CampaignException, IOException;
}
