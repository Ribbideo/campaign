package com.kencorhealth.campaign.dm.input;

import com.kencorhealth.campaign.dm.common.Input;
import com.kencorhealth.campaign.dm.entity.Participant;
import com.kencorhealth.campaign.dm.entity.ParticipantStatus;

public class ParticipantInput extends Input<Participant> {
    private String memberId;
    private String campaignId;
    private String clinicId;
    private ParticipantStatus status;

    @Override
    public Participant convert() {
        Participant retVal = new Participant();
        retVal.autoFill();
        
        retVal.setClinicId(clinicId);
        retVal.setCampaignId(campaignId);
        retVal.setMemberId(memberId);
        retVal.setStatus(status);
        
        return retVal;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
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
            "ParticipantInput{" + "memberId=" + memberId + ", status=" +
            status + ", campaignId=" + campaignId + ", clinicId=" +
            clinicId + '}';
    }
}
