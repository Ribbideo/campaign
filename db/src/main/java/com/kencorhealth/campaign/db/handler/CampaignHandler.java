package com.kencorhealth.campaign.db.handler;

import com.kencorhealth.campaign.dm.annotations.Exportable;
import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.dm.input.CampaignInput;
import java.util.List;

@Exportable
public interface CampaignHandler
    extends CampaignBasedHandler<Campaign, CampaignInput> {
    @Override
    default String collectionName() {
        return CAMPAIGN_COLLECTION;
    }

    Campaign findByClinicAndName(String clinicId, String name)
        throws NotFoundException, CampaignException;
    Campaign findByClinicAndId(String clinicId, String campaignId)
        throws NotFoundException, CampaignException;
    public List<Campaign> findByClinic(String clinicId)
        throws CampaignException;
}
