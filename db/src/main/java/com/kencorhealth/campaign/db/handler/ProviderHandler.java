package com.kencorhealth.campaign.db.handler;

import com.kencorhealth.campaign.dm.annotations.Exportable;
import com.kencorhealth.campaign.dm.provider.Provider;
import com.kencorhealth.campaign.dm.provider.ProviderType;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.dm.input.ProviderInput;

@Exportable
public interface ProviderHandler
    extends CampaignBasedHandler<Provider, ProviderInput> {
    @Override
    default String collectionName() {
        return PROVIDER_COLLECTION;
    }
    
    Provider findByNameAndType(String name, ProviderType type)
        throws NotFoundException, CampaignException;
}
