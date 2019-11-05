package com.kencorhealth.campaign.http.incoming.handler;

import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.InvalidException;
import com.kencorhealth.campaign.dm.input.IVRInput;
import com.kencorhealth.campaign.dm.input.SMSInput;

public interface ExecutionHandler extends CampaignBasedHandler {
    boolean dispatchSMS(
        String providerId, String campaignId, SMSInput input)
        throws InvalidException, CampaignException;
    boolean dispatchIVR(
        String providerId, String campaignId, IVRInput input)
        throws InvalidException, CampaignException;
}
