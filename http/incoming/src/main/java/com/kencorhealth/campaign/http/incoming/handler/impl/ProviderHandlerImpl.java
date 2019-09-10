package com.kencorhealth.campaign.http.incoming.handler.impl;

import com.kencorhealth.campaign.dm.provider.ProviderType;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.input.ProviderInput;
import com.kencorhealth.campaign.dm.output.ProviderDetail;
import com.kencorhealth.campaign.http.base.handler.impl.HttpBasedHandlerImpl;
import com.kencorhealth.campaign.http.incoming.handler.ProviderHandler;

public class ProviderHandlerImpl extends HttpBasedHandlerImpl
    implements ProviderHandler {
    @Override
    public String create(ProviderInput input) throws CampaignException {
        return
            sendPost(
                null,
                input,
                String.class,
                PROVIDER
            );
    }

    @Override
    public ProviderDetail getByNameAndType(String name, ProviderType type)
        throws CampaignException {
        return
            sendGet(
                null,
                ProviderDetail.class,
                PROVIDER,
                BY_NAME,
                name,
                type.name()
            );
    }

    @Override
    public ProviderDetail getById(String providerId) throws CampaignException {
        return
            sendGet(
                null,
                ProviderDetail.class,
                PROVIDER,
                providerId
            );
    }
}
