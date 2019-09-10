package com.kencorhealth.campaign.service.api.provider;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import javax.ws.rs.Path;

public class ProviderNameResource extends CampaignBasedResource {
    @Path("/" + TYPE_ENDPOINT)
    public ProviderTypeResource geProviderTypeResource() {
        return new ProviderTypeResource();
    }
}
