package com.kencorhealth.campaign.http.incoming.handler.impl;

import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.ribbideo.dm.exceptions.InvalidException;
import com.kencorhealth.campaign.dm.input.IVRInput;
import com.kencorhealth.campaign.dm.input.SMSInput;
import com.kencorhealth.campaign.http.base.handler.impl.HttpBasedHandlerImpl;
import com.kencorhealth.campaign.http.incoming.CHIFactory;
import com.kencorhealth.campaign.http.incoming.handler.CampaignHandler;
import com.kencorhealth.campaign.http.incoming.handler.ExecutionHandler;

public class ExecutionHandlerImpl extends HttpBasedHandlerImpl
    implements ExecutionHandler {
    @Override
    public boolean dispatchSMS(
        String providerId,
        String campaignId,
        SMSInput input) throws InvalidException, CampaignException {
        ensureCampaignExistence(providerId, campaignId);
        
        return
            sendPut(
                null,
                input,
                Boolean.class,
                PROVIDER,
                providerId,
                CAMPAIGN,
                campaignId,
                EXECUTION,
                SMS
            );
    }

    @Override
    public boolean dispatchIVR(
        String providerId, String campaignId, IVRInput input)
        throws InvalidException, CampaignException {
        ensureCampaignExistence(providerId, campaignId);
        
        return
            sendPut(
                null,
                input,
                Boolean.class,
                PROVIDER,
                providerId,
                CAMPAIGN,
                campaignId,
                EXECUTION,
                IVR
            );
    }
    
    private void ensureCampaignExistence(String providerId, String campaignId)
        throws InvalidException, CampaignException {
        Campaign campaign = null;
        
        try (CampaignHandler ch = CHIFactory.internal(CampaignHandler.class)) {
            campaign = ch.getById(providerId, campaignId);
        }
        
        if (campaign == null) {
            String message = "Campaign for Id '" + campaignId + "' not found";
            throw new InvalidException(message);
        }
    }
}
