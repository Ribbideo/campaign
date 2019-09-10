package com.kencorhealth.campaign.twilio.handler;

import com.kencorhealth.campaign.dm.entity.Participant;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.util.Map;

public interface TwilioHandler extends TwilioBasedHandler {
    String executeSms(
        String sms, String from, Participant to, Map<String, Object> data)
        throws CampaignException;
}
