package com.kencorhealth.campaign.dm.delegate;

import com.kencorhealth.campaign.dm.input.IVRInput;
import java.util.Set;

public class CampaignIVR extends CampaignBasedData {
    private Set<String> participantIds;

    public CampaignIVR() {
        super();
    }
    
    public CampaignIVR(IVRInput input) {
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
            "CampaignIVR{" + "participantIds=" + participantIds +
            "}, " + super.toString();
    }
}
