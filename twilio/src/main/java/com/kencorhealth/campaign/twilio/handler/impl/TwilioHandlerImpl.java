package com.kencorhealth.campaign.twilio.handler.impl;

import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.handler.MemberHandler;
import com.kencorhealth.campaign.dm.provider.Member;
import com.kencorhealth.campaign.dm.entity.Participant;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.twilio.TwilioFactory;
import com.kencorhealth.campaign.twilio.handler.TwilioHandler;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import freemarker.template.Template;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import com.kencorhealth.campaign.db.handler.AuthTokenHandler;
import com.kencorhealth.campaign.dm.common.PhoneType;

public class TwilioHandlerImpl implements TwilioHandler {
    @Override
    public String executeSms(
        String sms, String from, Participant to, Map<String, Object> data)
        throws CampaignException {
        String retVal = null;
        
        try (AuthTokenHandler pth =
             CampaignFactory.get(AuthTokenHandler.class);
             MemberHandler mh =
             CampaignFactory.get(MemberHandler.class)) {
            Template t =
                new Template(
                    "smsTemplate",
                    new StringReader(sms),
                    TwilioFactory.getFreeMarkerConfig()
                );

            Writer out = new StringWriter();
            
            /*ParticipantTokenInput pti = new ParticipantTokenInput();
            pti.setParticipantId(to.getId());*/
            
            Member member = mh.findById(to.getMemberId());
            
            /*ParticipantToken pt = pth.add(pti);
            
            Document memberDoc = JsonUtil.asDoc(member);
            memberDoc.put("token", pt.getId());
            
            data.put("member", memberDoc);*/

            t.process(data, out);

            String filledSms = out.toString();

            Message message =
                Message.creator(
                    new PhoneNumber(member.getPhoneNumber()),
                    new PhoneNumber(from), 
                    filledSms
                ).create();

            retVal = message.getSid();
        } catch (Exception e) {
            throw new CampaignException(e);
        }
        
        return retVal;
    }
    
    @Override
    public PhoneType getPhoneType(String number) throws CampaignException {
        PhoneType retVal = null;
        
        try {
            com.twilio.rest.lookups.v1.PhoneNumber pn =
                com.twilio.rest.lookups.v1.PhoneNumber
                .fetcher(new com.twilio.type.PhoneNumber(number))
                .setType("carrier")
                .fetch();

            retVal = PhoneType.from(pn.getCarrier().get("type"));
        } catch (Exception e) {
            retVal = PhoneType.MOBILE;
        }
        
        return retVal;
    }
}
