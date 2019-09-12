package com.kencorhealth.campaign.service.api.campaign.execution;

import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.dm.delegate.CampaignSMS;
import com.kencorhealth.campaign.dm.input.SMSInput;
import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import com.kencorhealth.campaign.mq.CMQFactory;
import io.dropwizard.auth.Auth;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SMSResource extends CampaignBasedResource {
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response dispatchExecCampaignSMS(
        @Auth AuthToken at,
        @PathParam(CAMPAIGN_ID) String campaignId,
        SMSInput smsInput) {
        Response retVal = null;

        try {
            CampaignSMS input = new CampaignSMS(smsInput);
            input.setCampaignId(campaignId);
            input.setProviderId(at.getProviderId());
            
            CMQFactory.getDispatcher().dispatchExecCampaignSMS(input);
            
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(true)
                    .build();
        } catch (Exception e) {
            retVal = fromException(e);
        }

        return retVal;
    }
}
