package com.kencorhealth.campaign.service.api.campaign.runtime;

import com.kencorhealth.campaign.service.api.campaign.runtime.sms.SMSResource;
import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import javax.ws.rs.Path;

public class RuntimeResource extends CampaignBasedResource {
    @Path("/" + SMS)
    public SMSResource getSMSResource() {
        return new SMSResource();
    }
}
