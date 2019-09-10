package com.kencorhealth.campaign.service.api.campaign.runtime.sms;

import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.CampaignHandler;
import com.kencorhealth.campaign.db.handler.ParticipantHandler;
import com.kencorhealth.campaign.db.handler.ParticipantTokenHandler;
import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.dm.entity.Participant;
import com.kencorhealth.campaign.dm.entity.ParticipantToken;
import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SMSResource extends CampaignBasedResource {
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response acknowledge(@QueryParam(TOKEN) String token) {
        Response retVal = null;

        try (ParticipantTokenHandler pth =
             CampaignFactory.get(ParticipantTokenHandler.class);
             ParticipantHandler ph =
             CampaignFactory.get(ParticipantHandler.class);
             CampaignHandler ch =
             CampaignFactory.get(CampaignHandler.class)) {
            ParticipantToken pt = pth.visit(token);
            Participant participant = ph.findById(pt.getParticipantId());
            Campaign campaign = ch.findById(participant.getCampaignId());
            
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(campaign.getDelivery().getSms().getNav())
                    .build();
            
        } catch (Exception e) {
            retVal = fromException(e);
        }

        return retVal;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response archive(@QueryParam(TOKEN) String token) {
        Response retVal = null;

        try (ParticipantTokenHandler pth =
             CampaignFactory.get(ParticipantTokenHandler.class)) {
            pth.archive(token);
            
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(true)
                    .build();
            
        } catch (Exception e) {
            retVal = fromException(e);
        }

        return retVal;
    }
}
