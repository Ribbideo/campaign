package com.kencorhealth.campaign.service.api.provider.campaign.participant;

import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.MemberHandler;
import com.kencorhealth.campaign.db.handler.ParticipantHandler;
import com.kencorhealth.campaign.dm.provider.Member;
import com.kencorhealth.campaign.dm.entity.Participant;
import com.kencorhealth.campaign.dm.input.ParticipantInput;
import com.kencorhealth.campaign.dm.output.ParticipantDetail;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ParticipantResource extends CampaignBasedResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createParticipant(
        @PathParam(PROVIDER_ID) String providerId,
        @PathParam(CAMPAIGN_ID) String campaignId,
        ParticipantInput input) {
        Response retVal = null;

        try (ParticipantHandler ph =
             CampaignFactory.get(ParticipantHandler.class)) {
            input.setCampaignId(campaignId);
            input.setProviderId(providerId);
            
            String participantId = ph.add(input);
            
            retVal =
                Response
                    .status(HttpServletResponse.SC_CREATED)
                    .entity(participantId)
                    .build();
        } catch (Exception e) {
            retVal = fromException(e);
        }

        return retVal;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getParticipants(
        @PathParam(PROVIDER_ID) String providerId,
        @PathParam(CAMPAIGN_ID) String campaignId) {
        Response retVal = null;
        
        try (ParticipantHandler ph =
             CampaignFactory.get(ParticipantHandler.class)) {
            Map<String, Object> filter = new HashMap();
            filter.put(PROVIDER_ID, providerId);
            filter.put(CAMPAIGN_ID, campaignId);
            
            List<Participant> participants = ph.findMany(filter);
            
            List<ParticipantDetail> details = new ArrayList();
            
            try (MemberHandler mh = CampaignFactory.get(MemberHandler.class)) {
                participants.forEach((participant) -> {
                    ParticipantDetail pd = new ParticipantDetail();
                    pd.fillFrom(participant);

                    String memberId = participant.getMemberId();

                    try {
                        Member member = mh.findById(providerId, memberId);

                        pd.setMember(member);

                        details.add(pd);
                    } catch (Exception ex) {
                        // Should not happen
                        ex.printStackTrace();
                    }
                });
            }
            
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(details)
                    .build();
        } catch (Exception e) {
           retVal = fromException(e);
        }
        
        return retVal;
    }

    @Path("/" + PARTICIPANT_ID_ENDPOINT)
    public ParticipantIdResource geParticipantIdResource() {
        return new ParticipantIdResource();
    }
}
