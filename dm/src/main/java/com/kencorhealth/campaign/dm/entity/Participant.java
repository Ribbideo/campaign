package com.kencorhealth.campaign.dm.entity;

import com.kencorhealth.campaign.dm.common.Identified;

public class Participant extends Identified {
    private String memberId;
    private String campaignId;
    private String providerId;
    private ParticipantStatus status;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public ParticipantStatus getStatus() {
        return status;
    }

    public void setStatus(ParticipantStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return
            "Participant{" + "memberId=" + memberId + ", status=" +
            status + ", campaignId=" + campaignId + ", providerId=" +
            providerId + "}, " + super.toString();
    }
}
