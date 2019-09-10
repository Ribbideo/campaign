package com.kencorhealth.campaign.service.api.provider;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.ProviderHandler;
import com.kencorhealth.campaign.dm.input.ProviderInput;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ProviderResource extends CampaignBasedResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProvider(ProviderInput input) {
        Response retVal = null;

        try (ProviderHandler ph = CampaignFactory.get(ProviderHandler.class)) {
            String campaignId = ph.add(input);
            retVal =
                Response
                    .status(HttpServletResponse.SC_CREATED)
                    .entity(campaignId)
                    .build();
        } catch (Exception e) {
            retVal = fromException(e);
        }

        return retVal;
    }

    @Path("/" + BY_NAME)
    public ByNameResource geByNameResource() {
        return new ByNameResource();
    }

    @Path("/" + PROVIDER_ID_ENDPOINT)
    public ProviderIdResource geProviderIdResource() {
        return new ProviderIdResource();
    }
}
