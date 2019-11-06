package com.kencorhealth.campaign.db.handler;

import com.kencorhealth.campaign.dm.annotations.Exportable;
import com.kencorhealth.campaign.dm.entity.Participant;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.dm.input.ParticipantInput;
import java.util.List;

@Exportable
public interface ParticipantHandler
    extends CampaignBasedHandler<Participant, ParticipantInput> {
    @Override
    default String collectionName() {
        return PARTICIPANT_COLLECTION;
    }
    
    List<Participant> findByClinicAndCampaign(
        String clinicId, String campaignId)
        throws NotFoundException, CampaignException;
    
    Participant findById(
        String clinicId, String campaignId, String participantId)
        throws NotFoundException, CampaignException;
}
