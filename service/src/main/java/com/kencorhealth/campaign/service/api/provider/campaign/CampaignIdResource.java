package com.kencorhealth.campaign.service.api.provider.campaign;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import com.kencorhealth.campaign.service.api.provider.campaign.participant.ParticipantResource;
import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.CampaignHandler;
import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.service.api.provider.campaign.execution.ExecutionResource;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CampaignIdResource extends CampaignBasedResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCampaign(
        @PathParam(PROVIDER_ID) String providerId,
        @PathParam(CAMPAIGN_ID) String campaignId) {
        Response retVal = null;
        
        try (CampaignHandler ch = CampaignFactory.get(CampaignHandler.class)) {
            Campaign campaign = ch.findByProviderAndId(providerId, campaignId);
            
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(campaign)
                    .build();
        } catch (Exception e) {
           retVal = fromException(e);
        }
        
        return retVal;
    }

    @Path("/" + PARTICIPANT)
    public ParticipantResource getParticipantResource() {
        return new ParticipantResource();
    }

    @Path("/" + EXECUTION)
    public ExecutionResource getExecutionResource() {
        return new ExecutionResource();
    }
}