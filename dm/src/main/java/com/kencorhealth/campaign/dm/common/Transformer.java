package com.kencorhealth.campaign.dm.common;

import com.kencorhealth.campaign.dm.exception.CampaignException;

public interface Transformer {
    String transform(String input) throws CampaignException;
}
