package com.kencorhealth.campaign.service.api.campaign.nav;

import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.CampaignHandler;
import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.dm.delivery.nav.Nav;
import com.kencorhealth.campaign.dm.delivery.nav.QualifiedNav;
import com.kencorhealth.campaign.dm.delivery.web.WorkflowData;
import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import io.dropwizard.auth.Auth;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.kencorhealth.campaign.db.handler.WorkflowDataHandler;

public class NavResource extends CampaignBasedResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFirstNav(
        @Auth AuthToken at,
        @PathParam(CAMPAIGN_ID) String campaignId,
        @Context UriInfo uriInfo) {
        Response retVal = null;

        try (CampaignHandler ch =
             CampaignFactory.get(CampaignHandler.class);
             WorkflowDataHandler wdh =
             CampaignFactory.get(WorkflowDataHandler.class)) {
            Campaign campaign = ch.findById(campaignId);
            
            Nav nav = campaign.getDelivery().getWeb().getNav().get(0);
            
            QualifiedNav qn = new QualifiedNav();
            qn.setNav(nav);
            
            WorkflowData unused =
                wdh.createOrGetUnused(
                    at.clinicId(),
                    campaignId
                );
            
            qn.setContainerId(unused.getId());
            
            NavUtil.transform(qn, at);
            
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(qn)
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
