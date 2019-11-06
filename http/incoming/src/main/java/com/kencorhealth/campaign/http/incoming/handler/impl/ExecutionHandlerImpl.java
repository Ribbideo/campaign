package com.kencorhealth.campaign.http.incoming.handler.impl;

import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.InvalidException;
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
        String clinicId,
        String campaignId,
        SMSInput input) throws InvalidException, CampaignException {
        ensureCampaignExistence(clinicId, campaignId);
        
        return
            sendPut(null,
                input,
                Boolean.class,
                CLINIC,
                clinicId,
                CAMPAIGN,
                campaignId,
                EXECUTION,
                SMS
            );
    }

    @Override
    public boolean dispatchIVR(
        String clinicId, String campaignId, IVRInput input)
        throws InvalidException, CampaignException {
        ensureCampaignExistence(clinicId, campaignId);
        
        return
            sendPut(null,
                input,
                Boolean.class,
                CLINIC,
                clinicId,
                CAMPAIGN,
                campaignId,
                EXECUTION,
                IVR
            );
    }
    
    private void ensureCampaignExistence(String clinicId, String campaignId)
        throws InvalidException, CampaignException {
        Campaign campaign = null;
        
        try (CampaignHandler ch = CHIFactory.internal(CampaignHandler.class)) {
            campaign = ch.getById(clinicId, campaignId);
        }
        
        if (campaign == null) {
            String message = "Campaign for Id '" + campaignId + "' not found";
            throw new InvalidException(message);
        }
    }
}
