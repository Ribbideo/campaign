package com.kencorhealth.campaign.service.api.provider.member;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import javax.ws.rs.Path;

public class LastNameResource extends CampaignBasedResource {
    @Path("/" + FIRST_NAME_ENDPOINT)
    public FirstNameResource geFirstNameResource() {
        return new FirstNameResource();
    }
}
