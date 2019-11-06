package com.kencorhealth.campaign.service.api.campaign;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.CampaignHandler;
import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.dm.entity.Campaign;
import io.dropwizard.auth.Auth;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CampaignNameResource extends CampaignBasedResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCampaign(
        @Auth AuthToken at,
        @PathParam(NAME) String name) {
        Response retVal = null;
        
        try (CampaignHandler ch = CampaignFactory.get(CampaignHandler.class)) {
            Campaign campaign =
                ch.findByClinicAndName(
                    at.clinicId(),
                    name
                );
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
}
