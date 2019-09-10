package com.kencorhealth.campaign.service.api.campaign;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import com.kencorhealth.campaign.service.api.campaign.runtime.RuntimeResource;
import static com.kencorhealth.campaign.service.common.CampaignConstants.CAMPAIGN_ID_ENDPOINT;
import javax.ws.rs.Path;

public class CampaignResource extends CampaignBasedResource {
    @Path("/" + RUNTIME)
    public RuntimeResource getRuntimeResource() {
        return new RuntimeResource();
    }

    @Path("/" + CAMPAIGN_ID_ENDPOINT)
    public CampaignIdResource getCampaignIdResource() {
        return new CampaignIdResource();
    }
}
