package com.kencorhealth.campaign.service.api.provider.member;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.MemberHandler;
import com.kencorhealth.campaign.dm.provider.Member;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class FirstNameResource extends CampaignBasedResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByName(
        @PathParam(PROVIDER_ID) String providerId,
        @PathParam(LAST_NAME) String lastName,
        @PathParam(FIRST_NAME) String firstName) {
        Response retVal = null;
        
        try (MemberHandler mh = CampaignFactory.get(MemberHandler.class)) {
            Member member = mh.findByName(providerId, lastName, firstName);
            
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(member)
                    .build();
        } catch (Exception e) {
           retVal = fromException(e);
        }
        
        return retVal;
    }
}
