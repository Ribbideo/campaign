package com.kencorhealth.campaign.db.handler;

import com.kencorhealth.campaign.dm.annotations.Exportable;
import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.dm.input.CampaignInput;

@Exportable
public interface CampaignHandler
    extends CampaignBasedHandler<Campaign, CampaignInput> {
    @Override
    default String collectionName() {
        return CAMPAIGN_COLLECTION;
    }

    Campaign findByProviderAndName(String providerId, String name)
        throws NotFoundException, CampaignException;
    Campaign findByProviderAndId(String providerId, String campaignId)
        throws NotFoundException, CampaignException;
}
