package com.kencorhealth.campaign.service.api.campaign;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.CampaignHandler;
import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.service.api.campaign.execution.ExecutionResource;
import com.kencorhealth.campaign.service.api.campaign.file.FileResource;
import com.kencorhealth.campaign.service.api.campaign.nav.NavResource;
import com.kencorhealth.campaign.service.api.campaign.participant.ParticipantResource;
import static com.kencorhealth.campaign.service.common.CampaignConstants.FILE;
import io.dropwizard.auth.Auth;
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
        @Auth AuthToken at,
        @PathParam(CAMPAIGN_ID) String campaignId) {
        Response retVal = null;
        
        try (CampaignHandler ch = CampaignFactory.get(CampaignHandler.class)) {
            Campaign campaign = ch.findById(campaignId);
            
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
    
    @Path("/" + FILE)
    public FileResource getFileResource() {
        return new FileResource();
    }

    @Path("/" + NAV)
    public NavResource getNavResource() {
        return new NavResource();
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
