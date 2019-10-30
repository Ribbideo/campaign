package com.kencorhealth.campaign.service.api.file;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import javax.ws.rs.Path;

public class FileResource extends CampaignBasedResource {
    @Path("/" + FILE_ID_ENDPOINT)
    public FileIdResource getFileIdResource() {
        return new FileIdResource();
    }
}
