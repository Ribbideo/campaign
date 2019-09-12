package com.kencorhealth.campaign.service.api.campaign.nav;

import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.CampaignHandler;
import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.dm.delivery.nav.Nav;
import com.kencorhealth.campaign.dm.delivery.nav.QualifiedNav;
import com.kencorhealth.campaign.dm.delivery.script.ScriptContext;
import com.kencorhealth.campaign.dm.delivery.script.ScriptInput;
import com.kencorhealth.campaign.dm.delivery.web.WebMethod;
import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import io.dropwizard.auth.Auth;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class NavIdResource extends CampaignBasedResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNav(
        @Auth AuthToken at,
        @PathParam(CAMPAIGN_ID) String campaignId,
        @PathParam(NAV_ID) String navId,
        @QueryParam(CONTAINER_ID) String containerId,
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
    public Response getPutNav(
        @Auth AuthToken at,
        Map<String, Object> data,
        @QueryParam(CONTAINER_ID) String containerId,
        @PathParam(CAMPAIGN_ID) String campaignId,
        @PathParam(NAV_ID) String navId,
        @Context UriInfo uriInfo) {
        return doProcureNav(campaignId, containerId, navId, at, data);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPostNav(
        @Auth AuthToken at,
        Map<String, Object> data,
        @QueryParam(CONTAINER_ID) String containerId,
        @PathParam(CAMPAIGN_ID) String campaignId,
        @PathParam(NAV_ID) String navId,
        @Context UriInfo uriInfo) {
        return doProcureNav(campaignId, containerId, navId, at, data);
    }
    
    private Response doProcureNav(
        String campaignId,
        String containerId,
        String navId,
        AuthToken at,
        Map<String, Object> formData) {
        Response retVal = null;

        try (CampaignHandler ch =
             CampaignFactory.get(CampaignHandler.class)) {
            Campaign campaign = ch.findById(campaignId);
            
            WebMethod web = campaign.getDelivery().getWeb();
            
            ScriptContext contextData = new ScriptContext();
            contextData.setCampaignId(campaignId);
            contextData.setContainerId(containerId);
            contextData.setProviderId(at.getProviderId());
            
            ScriptInput scriptInput = new ScriptInput();
            
            scriptInput.setFormData(
                formData != null ?
                formData: new HashMap()
            );
            
            scriptInput.setContext(contextData);
            
            scriptInput.setNav(web.findById(navId));
            
            QualifiedNav child =
                web.findNextById(
                    navId,
                    scriptInput,
                    ExUtil.getExecutor()
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
