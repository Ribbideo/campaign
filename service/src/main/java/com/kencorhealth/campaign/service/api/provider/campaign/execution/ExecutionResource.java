package com.kencorhealth.campaign.service.api.provider.campaign.execution;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import javax.ws.rs.Path;

public class ExecutionResource extends CampaignBasedResource {
    @Path("/" + SMS)
    public SMSResource getSMSResource() {
        return new SMSResource();
    }

    @Path("/" + IVR)
    public IVRResource getIVRResource() {
        return new IVRResource();
    }
}
