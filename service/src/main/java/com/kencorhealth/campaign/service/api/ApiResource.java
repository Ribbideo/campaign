package com.kencorhealth.campaign.service.api;

import com.kencorhealth.campaign.service.api.auth.AuthResource;
import com.kencorhealth.campaign.service.api.campaign.CampaignResource;
import com.kencorhealth.campaign.service.api.clinic.ClinicResource;
import com.kencorhealth.campaign.service.api.file.FileResource;
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

    @Path("/" + FILE)
    public FileResource getFileResource() {
        return new FileResource();
    }

    @Path("/" + CLINIC)
    public ClinicResource getClinicResource() {
        return new ClinicResource();
    }
}