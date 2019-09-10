package com.kencorhealth.campaign.service.api.provider.campaign;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.CampaignHandler;
import com.kencorhealth.campaign.dm.input.CampaignInput;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CampaignResource extends CampaignBasedResource {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCampaign(
        @PathParam(PROVIDER_ID) String providerId,
        CampaignInput input) {
        Response retVal = null;
        
        try (CampaignHandler ch = CampaignFactory.get(CampaignHandler.class)) {
            input.setProviderId(providerId);
            
            String campaignId = ch.add(input);
            retVal =
                Response
                    .status(HttpServletResponse.SC_CREATED)
                    .entity(campaignId)
                    .build();
        } catch (Exception e) {
           retVal = fromException(e);
         }
        
        return retVal;
    }
    
    @Path("/" + BY_NAME)
    public ByNameResource getByNameResource() {
        return new ByNameResource();
    }

    @Path("/" + CAMPAIGN_ID_ENDPOINT)
    public CampaignIdResource getCampaignIdResource() {
        return new CampaignIdResource();
    }
}
