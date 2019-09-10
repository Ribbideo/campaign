package com.kencorhealth.campaign.dm.delegate;

import com.kencorhealth.campaign.dm.input.SMSInput;
import java.util.Set;

public class CampaignSMS extends CampaignBasedData {
    private Set<String> participantIds;
    
    public CampaignSMS() {
        super();
    }
    
    public CampaignSMS(SMSInput input) {
        super();
        participantIds = input.getParticipantIds();
    }

    public Set<String> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(Set<String> participantIds) {
        this.participantIds = participantIds;
    }

    @Override
    public String toString() {
        return
            "CampaignSMS{" + "participantIds=" + participantIds +
            "}, " + super.toString();
    }
}
