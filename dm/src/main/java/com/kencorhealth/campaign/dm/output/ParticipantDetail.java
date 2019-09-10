package com.kencorhealth.campaign.dm.output;

import com.kencorhealth.campaign.dm.common.Identified;
import com.kencorhealth.campaign.dm.provider.Member;
import com.kencorhealth.campaign.dm.entity.Participant;
import com.kencorhealth.campaign.dm.entity.ParticipantStatus;

public class ParticipantDetail extends Identified {
    private String campaignId;
    private ParticipantStatus status;
    private Member member;

    public void fillFrom(Participant another) {
        super.fillFrom(another);
        
        campaignId = another.getCampaignId();
        status = another.getStatus();
    }
    
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    
    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
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
            "ParticipantDetail{" + "campaignId=" + campaignId +
            ", status=" + status + ", member=" + member + "}, " +
            super.toString();
    }
}
