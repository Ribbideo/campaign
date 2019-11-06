package com.kencorhealth.campaign.http.incoming.handler.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.input.CampaignInput;
import com.kencorhealth.campaign.http.base.handler.impl.HttpBasedHandlerImpl;
import com.kencorhealth.campaign.http.incoming.handler.CampaignHandler;
import java.util.List;

public class CampaignHandlerImpl extends HttpBasedHandlerImpl
    implements CampaignHandler {
    @Override
    public String create(CampaignInput input) throws CampaignException {
        return
            sendPost(
                null,
                input,
                String.class,
                CLINIC,
                input.getClinicId(),
                CAMPAIGN
            );
    }

    @Override
    public Campaign getByName(String clinicId, String name)
        throws CampaignException {
        return
            sendGet(
                null,
                Campaign.class,
                CLINIC,
                clinicId,
                CAMPAIGN,
                BY_NAME,
                name
            );
    }

    @Override
    public Campaign getById(String clinicId, String campaignId)
        throws CampaignException {
        return
            sendGet(
                null,
                Campaign.class,
                CLINIC,
                clinicId,
                CAMPAIGN,
                campaignId
            );
    }

    @Override
    public List<Campaign> getByClinic(String clinicId)
        throws CampaignException {
        return
            sendGet(
                null,
                new TypeReference<List<Campaign>>() {},
                CLINIC,
                clinicId,
                CAMPAIGN
            );
    }
}
