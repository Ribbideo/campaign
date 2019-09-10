package com.kencorhealth.campaign.http.incoming.handler;

import com.kencorhealth.campaign.dm.provider.ProviderType;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.input.ProviderInput;
import com.kencorhealth.campaign.dm.output.ProviderDetail;

public interface ProviderHandler extends CampaignBasedHandler {
    String create(ProviderInput input) throws CampaignException;
    ProviderDetail getById(String providerId) throws CampaignException;
    ProviderDetail getByNameAndType(String name, ProviderType type)
        throws CampaignException;
}
