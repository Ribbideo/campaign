package com.kencorhealth.campaign.dm.input;

import com.kencorhealth.campaign.dm.common.Identified;
import com.kencorhealth.campaign.dm.common.Input;
import java.util.Set;

public abstract class FlowBasedInput extends Input<Identified> {
    private Set<String> participantIds;

    public Set<String> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(Set<String> participantIds) {
        this.participantIds = participantIds;
    }

    @Override
    public String toString() {
        return "FlowBasedInput{" + "participantIds=" + participantIds + '}';
    }
}
