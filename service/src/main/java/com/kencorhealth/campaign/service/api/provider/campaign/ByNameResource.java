package com.kencorhealth.campaign.service.api.provider.campaign;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import javax.ws.rs.Path;

public class ByNameResource extends CampaignBasedResource {
    @Path("/" + NAME_ENDPOINT)
    public CampaignNameResource geCampaignNameResource() {
        return new CampaignNameResource();
    }
}
