package com.kencorhealth.campaign.http.incoming.handler.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.input.ParticipantInput;
import com.kencorhealth.campaign.dm.output.ParticipantDetail;
import com.kencorhealth.campaign.http.base.handler.impl.HttpBasedHandlerImpl;
import com.kencorhealth.campaign.http.incoming.handler.ParticipantHandler;
import java.util.List;

public class ParticipantHandlerImpl extends HttpBasedHandlerImpl
    implements ParticipantHandler {
    @Override
    public String create(ParticipantInput input) throws CampaignException {
        return
            sendPost(null,
                input,
                String.class,
                CLINIC,
                input.getClinicId(),
                CAMPAIGN,
                input.getCampaignId(),
                PARTICIPANT
            );
    }

    @Override
    public ParticipantDetail getById(
        String clinicId,
        String campaignId,
        String participantId) throws CampaignException {
        return
            sendGet(
                null,
                ParticipantDetail.class,
                CLINIC,
                clinicId,
                CAMPAIGN,
                campaignId,
                PARTICIPANT,
                participantId
            );
    }

    @Override
    public List<ParticipantDetail> getByCampaign(
        String clinicId, String campaignId) throws CampaignException {
        return
            sendGet(
                null,
                new TypeReference<List<ParticipantDetail>>() {},
                CLINIC,
                clinicId,
                CAMPAIGN,
                campaignId,
                PARTICIPANT
            );
    }
}
