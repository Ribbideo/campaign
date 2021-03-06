package com.kencorhealth.campaign.service.api.clinic.member;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.MemberHandler;
import com.kencorhealth.campaign.dm.clinic.Member;
import com.kencorhealth.campaign.dm.input.MemberInput;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class MemberResource extends CampaignBasedResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMember(
        @PathParam(CLINIC_ID) String clinicId,
        MemberInput input) {
        Response retVal = null;

        try (MemberHandler mh = CampaignFactory.get(MemberHandler.class)) {
            input.setClinicId(clinicId);
            Member m = mh.add(input);
            retVal =
                Response
                    .status(HttpServletResponse.SC_CREATED)
                    .entity(m.getId())
                    .build();
        } catch (Exception e) {
            retVal = fromException(e);
        }

        return retVal;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMembers(
        @PathParam(CLINIC_ID) String clinicId) {
        Response retVal = null;

        try (MemberHandler mh = CampaignFactory.get(MemberHandler.class)) {
            List<Member> members = mh.findByClinic(clinicId);
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(members)
                    .build();
        } catch (Exception e) {
            retVal = fromException(e);
        }

        return retVal;
    }

    @Path("/" + MEMBER_ID_ENDPOINT)
    public MemberIdResource geMemberIdResource() {
        return new MemberIdResource();
    }
}
