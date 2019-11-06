package com.kencorhealth.campaign.http.incoming.handler;

import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.InvalidException;
import com.kencorhealth.campaign.dm.input.IVRInput;
import com.kencorhealth.campaign.dm.input.SMSInput;

public interface ExecutionHandler extends CampaignBasedHandler {
    boolean dispatchSMS(
        String clinicId, String campaignId, SMSInput input)
        throws InvalidException, CampaignException;
    boolean dispatchIVR(
        String clinicId, String campaignId, IVRInput input)
        throws InvalidException, CampaignException;
}
