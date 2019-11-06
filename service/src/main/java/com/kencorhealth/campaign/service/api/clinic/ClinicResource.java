package com.kencorhealth.campaign.service.api.clinic;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import javax.ws.rs.Path;

public class ClinicResource extends CampaignBasedResource {
    @Path("/" + CLINIC_ID_ENDPOINT)
    public ClinicIdResource geClinicIdResource() {
        return new ClinicIdResource();
    }
}
