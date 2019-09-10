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
                PROVIDER,
                input.getProviderId(),
                CAMPAIGN
            );
    }

    @Override
    public Campaign getByName(String providerId, String name)
        throws CampaignException {
        return
            sendGet(
                null,
                Campaign.class,
                PROVIDER,
                providerId,
                CAMPAIGN,
                BY_NAME,
                name
            );
    }

    @Override
    public Campaign getById(String providerId, String campaignId)
        throws CampaignException {
        return
            sendGet(
                null,
                Campaign.class,
                PROVIDER,
                providerId,
                CAMPAIGN,
                campaignId
            );
    }

    @Override
    public List<Campaign> getByProvider(String providerId)
        throws CampaignException {
        return
            sendGet(
                null,
                new TypeReference<List<Campaign>>() {},
                PROVIDER,
                providerId,
                CAMPAIGN
            );
    }
}
