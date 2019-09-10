package com.kencorhealth.campaign.dm.input;

import com.kencorhealth.campaign.dm.common.Input;
import com.kencorhealth.campaign.dm.entity.ParticipantToken;

public class ParticipantTokenInput extends Input<ParticipantToken> {
    private String participantId;
    
    @Override
    public ParticipantToken convert() {
        ParticipantToken retVal = new ParticipantToken();

        retVal.autoFill();
        retVal.setParticipantId(participantId);
        
        return retVal;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    @Override
    public String toString() {
        return "ParticipantTokenInput{" + "campaignId=" + participantId + '}';
    }
}
