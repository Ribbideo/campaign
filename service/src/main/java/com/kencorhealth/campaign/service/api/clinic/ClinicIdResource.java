package com.kencorhealth.campaign.service.api.clinic;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import com.kencorhealth.campaign.service.api.clinic.member.MemberResource;
import javax.ws.rs.Path;

public class ClinicIdResource extends CampaignBasedResource {
    @Path("/" + MEMBER)
    public MemberResource getMemberResource() {
        return new MemberResource();
    }
}
