package com.kencorhealth.campaign.db.handler.impl;

import com.kencorhealth.campaign.db.handler.ParticipantTokenHandler;
import com.kencorhealth.campaign.dm.entity.ParticipantToken;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.DbException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.dm.input.ParticipantTokenInput;
import com.kencorhealth.campaign.mongo.handler.impl.MongoHandlerImpl;
import com.mongodb.MongoClient;

public class ParticipantTokenHandlerImpl
    extends MongoHandlerImpl<ParticipantToken, ParticipantTokenInput>
    implements ParticipantTokenHandler {
    public ParticipantTokenHandlerImpl(MongoClient mc) {
        super(mc);
    }

    @Override
    public void archive(String participantId)
        throws NotFoundException, DbException, CampaignException {
        ParticipantToken existing = findById(participantId);
        existing.setArchived(true);
        update(existing);
    }

    @Override
    public ParticipantToken visit(String participantId)
        throws NotFoundException, DbException, CampaignException {
        ParticipantToken retVal = findById(participantId);
        update(retVal);
        
        return retVal;
    }
}
