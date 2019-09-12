package com.kencorhealth.campaign.service.api.provider;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import com.kencorhealth.campaign.service.api.provider.member.MemberResource;
import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.ProviderHandler;
import com.kencorhealth.campaign.dm.provider.Provider;
import com.kencorhealth.campaign.dm.output.ProviderDetail;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ProviderIdResource extends CampaignBasedResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProvider(
        @PathParam(PROVIDER_ID) String providerId) {
        Response retVal = null;
        
        try (ProviderHandler ph = CampaignFactory.get(ProviderHandler.class)) {
            Provider provider = ph.findById(providerId);
            
            ProviderDetail pd = null;
            
            if (provider != null) {
                pd = provider.toDetail();
            }
            
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(pd)
                    .build();
        } catch (Exception e) {
            retVal = fromException(e);
        }
        
        return retVal;
    }
    
    @Path("/" + MEMBER)
    public MemberResource getMemberResource() {
        return new MemberResource();
    }
}
