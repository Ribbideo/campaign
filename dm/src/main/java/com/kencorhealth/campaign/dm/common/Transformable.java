package com.kencorhealth.campaign.dm.common;

import com.kencorhealth.campaign.dm.exception.CampaignException;

public interface Transformable {
    public void transformFrom(Transformer transformer) throws CampaignException;
}
