package com.kencorhealth.campaign.service.api.provider;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import javax.ws.rs.Path;

public class ByNameResource extends CampaignBasedResource {
    @Path("/" + NAME_ENDPOINT)
    public ProviderNameResource geProviderNameResource() {
        return new ProviderNameResource();
    }
}
