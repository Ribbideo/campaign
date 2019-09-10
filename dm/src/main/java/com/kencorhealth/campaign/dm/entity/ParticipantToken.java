package com.kencorhealth.campaign.dm.entity;

import com.kencorhealth.campaign.dm.common.Identified;

public class ParticipantToken extends Identified {
    private boolean archived;
    private String participantId;

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    @Override
    public String toString() {
        return
            "ParticipantToken{" + "archived=" + archived +
            ", participantId=" + participantId + "}, " + super.toString();
    }
}
