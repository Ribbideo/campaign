package com.kencorhealth.campaign.service.api.clinic.member;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.MemberHandler;
import com.kencorhealth.campaign.dm.clinic.Member;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class MemberIdResource extends CampaignBasedResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMember(
        @PathParam(CLINIC_ID) String clinicId,
        @PathParam(MEMBER_ID) String memberId) {
        Response retVal = null;
        
        try (MemberHandler mh = CampaignFactory.get(MemberHandler.class)) {
            Member member = mh.findById(clinicId, memberId);
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(member)
                    .build();
        } catch (Exception e) {
            retVal = fromException(e);
        }
        
        return retVal;
    }
}
