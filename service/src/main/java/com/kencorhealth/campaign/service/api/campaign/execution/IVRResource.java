package com.kencorhealth.campaign.service.api.campaign.execution;

import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.dm.delegate.CampaignIVR;
import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import com.kencorhealth.campaign.dm.input.IVRInput;
import com.kencorhealth.campaign.mq.CMQFactory;
import io.dropwizard.auth.Auth;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class IVRResource extends CampaignBasedResource {
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createParticipant(
        @Auth AuthToken at,
        @PathParam(CAMPAIGN_ID) String campaignId,
        IVRInput ivrInput) {
        Response retVal = null;

        try {
            CampaignIVR input = new CampaignIVR(ivrInput);
            
            input.setCampaignId(campaignId);
            input.setProviderId(at.getProviderId());
            
            CMQFactory.getDispatcher().dispatchExecCampaignIVR(input);
            
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
