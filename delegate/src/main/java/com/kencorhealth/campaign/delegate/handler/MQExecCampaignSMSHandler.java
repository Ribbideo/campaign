package com.kencorhealth.campaign.delegate.handler;

import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.CampaignHandler;
import com.kencorhealth.campaign.dm.delegate.CampaignSMS;
import com.kencorhealth.campaign.db.handler.ParticipantHandler;
import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.dm.entity.Delivery;
import com.kencorhealth.campaign.dm.entity.Participant;
import com.kencorhealth.campaign.mq.MessageHandler;
import com.kencorhealth.campaign.twilio.TwilioConfig;
import com.kencorhealth.campaign.twilio.TwilioFactory;
import com.kencorhealth.campaign.twilio.handler.TwilioHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQExecCampaignSMSHandler extends MessageHandler<CampaignSMS> {
    private final static Logger log =
        LoggerFactory.getLogger(MQExecCampaignSMSHandler.class);

    @Override
    public void process() throws Exception {
        CampaignSMS cs = data;
        
        TwilioConfig twilio = TwilioFactory.getTwilio();
        
        String from = twilio.getAdminNumber();
        
        String providerId = cs.getProviderId();
        String campaignId = cs.getCampaignId();
        Set<String> participantIds = cs.getParticipantIds();
        
        try (TwilioHandler th =
             TwilioFactory.get(TwilioHandler.class);
             ParticipantHandler ph =
             CampaignFactory.get(ParticipantHandler.class);
             CampaignHandler ch =
             CampaignFactory.get(CampaignHandler.class)) {
            Campaign campaign = ch.findByProviderAndId(providerId, campaignId);
            
            for (String participantId: participantIds) {
                Participant participant =
                    ph.findById(providerId, campaignId, participantId);
                Map<String, Object> twilioData = new HashMap();
                // TODO: Fill with data later if needed
                Delivery methods = campaign.getDelivery();
                
                String twilioResponse =
                    th.executeSms(
                        methods.getSms().getText(),
                        from,
                        participant,
                        twilioData
                    );

                log.debug("Twilio response: " + twilioResponse);
            }
        }
    }
}
