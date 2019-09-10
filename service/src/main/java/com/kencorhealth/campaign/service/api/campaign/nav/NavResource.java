package com.kencorhealth.campaign.service.api.campaign.nav;

import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.CampaignHandler;
import com.kencorhealth.campaign.dm.delivery.nav.Nav;
import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class NavResource extends CampaignBasedResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFirstNav(
        @PathParam(CAMPAIGN_ID) String campaignId,
        @PathParam(NAV_ID) String parentId,
        @Context UriInfo uriInfo) {
        Response retVal = null;

        try (CampaignHandler ch =
             CampaignFactory.get(CampaignHandler.class)) {
            Campaign campaign = ch.findById(campaignId);
            
            Nav item = campaign.getDelivery().getWeb().getNav().get(0);
            
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(item)
                    .build();
            
        } catch (Exception e) {
            retVal = fromException(e);
        }

        return retVal;
    }

    @Path("/" + NAV_ID_ENDPOINT)
    public NavIdResource getNavIdResource() {
        return new NavIdResource();
    }
}
