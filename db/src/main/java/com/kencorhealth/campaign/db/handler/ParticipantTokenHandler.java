package com.kencorhealth.campaign.db.handler;

import com.kencorhealth.campaign.dm.annotations.Exportable;
import com.kencorhealth.campaign.dm.entity.ParticipantToken;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.DbException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.dm.input.ParticipantTokenInput;

@Exportable
public interface ParticipantTokenHandler
    extends CampaignBasedHandler<ParticipantToken, ParticipantTokenInput> {
    @Override
    default String collectionName() {
        return PARTICIPANT_TOKEN_COLLECTION;
    }
    
    void archive(String participantId)
        throws NotFoundException, DbException, CampaignException;
    ParticipantToken visit(String participantId)
        throws NotFoundException, DbException, CampaignException;
}
