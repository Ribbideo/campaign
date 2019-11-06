package com.kencorhealth.campaign.service.api.campaign.participant;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.MemberHandler;
import com.kencorhealth.campaign.db.handler.ParticipantHandler;
import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.dm.clinic.Member;
import com.kencorhealth.campaign.dm.entity.Participant;
import com.kencorhealth.campaign.dm.output.ParticipantDetail;
import io.dropwizard.auth.Auth;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ParticipantIdResource extends CampaignBasedResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getParticipant(
        @Auth AuthToken at,
        @PathParam(CAMPAIGN_ID) String campaignId,
        @PathParam(PARTICIPANT_ID) String participantId) {
        Response retVal = null;
        
        try (ParticipantHandler ph =
             CampaignFactory.get(ParticipantHandler.class);
             MemberHandler mh =
             CampaignFactory.get(MemberHandler.class)) {
            String clinicId = at.clinicId();

            Participant participant =
                ph.findById(clinicId, campaignId, participantId);
            
            ParticipantDetail pd = new ParticipantDetail();
            pd.fillFrom(participant);

            String memberId = participant.getMemberId();

            try {
                Member member = mh.findById(clinicId, memberId);

                pd.setMember(member);
            } catch (Exception ex) {
                // Should not happen
                ex.printStackTrace();
            }
            
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(pd)
                    .build();
        } catch (Exception e) {
            retVal = fromException(e);
        }
        
        return retVal;
    }
}
