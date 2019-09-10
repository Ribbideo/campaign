package com.kencorhealth.campaign.service.api.campaign.nav;

import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.CampaignHandler;
import com.kencorhealth.campaign.dm.delivery.nav.Nav;
import com.kencorhealth.campaign.dm.delivery.type.WebMethod;
import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class NavIdResource extends CampaignBasedResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNav(
        @PathParam(CAMPAIGN_ID) String campaignId,
        @PathParam(NAV_ID) String navId,
        @Context UriInfo uriInfo) {
        Response retVal = null;

        try (CampaignHandler ch =
             CampaignFactory.get(CampaignHandler.class)) {
            Campaign campaign = ch.findById(campaignId);
            
            Nav item = campaign.getDelivery().getWeb().findById(navId);
            
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

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNextNav(
        Map<String, Object> data,
        @PathParam(CAMPAIGN_ID) String campaignId,
        @PathParam(NAV_ID) String navId,
        @Context UriInfo uriInfo) {
        Response retVal = null;

        try (CampaignHandler ch =
             CampaignFactory.get(CampaignHandler.class)) {
            Campaign campaign = ch.findById(campaignId);
            
            WebMethod web = campaign.getDelivery().getWeb();
            
            Nav child =
                web.findNextById(
                    navId,
                    data,
                    ExUtil.getExecutor(campaignId, navId),
                    ExUtil.getEvaluator()
                );
            
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(child)
                    .build();
            
        } catch (Exception e) {
            retVal = fromException(e);
        }

        return retVal;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submit(
        Map<String, Object> data,
        @PathParam(CAMPAIGN_ID) String campaignId,
        @PathParam(NAV_ID) String navId,
        @Context UriInfo uriInfo) {
        Response retVal = null;

        try (CampaignHandler ch =
             CampaignFactory.get(CampaignHandler.class)) {
            Campaign campaign = ch.findById(campaignId);
            
            WebMethod web = campaign.getDelivery().getWeb();
            
            Nav child =
                web.findNextById(
                    navId,
                    data,
                    ExUtil.getExecutor(campaignId, navId),
                    ExUtil.getEvaluator()
                );
            
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(child)
                    .build();
            
        } catch (Exception e) {
            retVal = fromException(e);
        }

        return retVal;
    }
}
