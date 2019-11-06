package com.kencorhealth.campaign.service.api.campaign;

import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.CampaignHandler;
import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.dm.input.CampaignInput;
import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import io.dropwizard.auth.Auth;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
        @Auth AuthToken at,
        CampaignInput input) {
        Response retVal = null;
        
        try (CampaignHandler ch = CampaignFactory.get(CampaignHandler.class)) {
            input.setClinicId(at.clinicId());
            
            Campaign campaign = ch.add(input);
            retVal =
                Response
                    .status(HttpServletResponse.SC_CREATED)
                    .entity(campaign.getId())
                    .build();
        } catch (Exception e) {
           retVal = fromException(e);
         }
        
        return retVal;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCampaigns(
        @Auth AuthToken at,
        @PathParam(NAME) String name) {
        Response retVal = null;
        
        try (CampaignHandler ch = CampaignFactory.get(CampaignHandler.class)) {
            List<Campaign> campaigns = ch.findByClinic(at.clinicId());
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(campaigns)
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
