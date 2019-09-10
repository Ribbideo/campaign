package com.kencorhealth.campaign.service.api.provider.member;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import javax.ws.rs.Path;

public class ByNameResource extends CampaignBasedResource {
    @Path("/" + LAST_NAME_ENDPOINT)
    public LastNameResource geLastNameResource() {
        return new LastNameResource();
    }
}
