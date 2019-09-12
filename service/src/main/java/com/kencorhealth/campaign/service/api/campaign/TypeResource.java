package com.kencorhealth.campaign.service.api.campaign;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.ProviderHandler;
import com.kencorhealth.campaign.dm.provider.Provider;
import com.kencorhealth.campaign.dm.provider.ProviderType;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TypeResource extends CampaignBasedResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProvider(
        @PathParam(NAME) String name,
        @PathParam(TYPE) String type) {
        Response retVal = null;
        
        try (ProviderHandler ph = CampaignFactory.get(ProviderHandler.class)) {
            Provider provider =
                ph.findByNameAndType(name, ProviderType.valueOf(type));
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(provider)
                    .build();
        } catch (Exception e) {
           retVal = fromException(e);
        }
        
        return retVal;
    }
}
