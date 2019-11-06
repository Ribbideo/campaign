package com.kencorhealth.campaign.http.incoming.handler;

import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.input.CampaignInput;
import java.util.List;

public interface CampaignHandler extends CampaignBasedHandler {
    String create(CampaignInput input) throws CampaignException;
    Campaign getByName(String clinicId, String name) throws CampaignException;
    Campaign getById(String clinicId, String campaignId) throws CampaignException;
    List<Campaign> getByClinic(String clinicId) throws CampaignException;
}
