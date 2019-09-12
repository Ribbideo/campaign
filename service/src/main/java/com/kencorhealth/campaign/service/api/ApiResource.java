package com.kencorhealth.campaign.service.api;

import com.kencorhealth.campaign.service.api.auth.AuthResource;
import com.kencorhealth.campaign.service.api.campaign.CampaignResource;
import com.kencorhealth.campaign.service.api.provider.ProviderResource;
import javax.ws.rs.Path;
import com.kencorhealth.campaign.service.common.CampaignConstants;

@Path(CampaignConstants.API)
public class ApiResource extends CampaignBasedResource {
    @Path("/" + CAMPAIGN)
    public CampaignResource getCampaignResource() {
        return new CampaignResource();
    }

    @Path("/" + AUTH)
    public AuthResource getAuthResource() {
        return new AuthResource();
    }

    @Path("/" + PROVIDER)
    public ProviderResource getProviderResource() {
        return new ProviderResource();
    }
}