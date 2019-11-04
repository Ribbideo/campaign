package com.kencorhealth.campaign.twilio.handler;

import com.kencorhealth.campaign.dm.annotations.Exportable;
import com.kencorhealth.campaign.dm.common.PhoneType;
import com.kencorhealth.campaign.dm.entity.Participant;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.util.Map;

@Exportable
public interface TwilioHandler extends TwilioBasedHandler {
    @Override
    public default String alias() {
        return "twilio";
    }
    
    String executeSms(
        String sms, String from, Participant to, Map<String, Object> data)
        throws CampaignException;
    PhoneType getPhoneType(String number) throws CampaignException;
}
