package com.kencorhealth.campaign.dm.common;

import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.util.Map;

public interface Executor<T> {
    T execute(Map<String, Object> data, Object... args)
        throws CampaignException;
}
