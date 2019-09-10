package com.kencorhealth.campaign.http.rpm.handler;

import com.kencorhealth.campaign.dm.annotations.Exportable;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.util.Map;

@Exportable
public interface UserHandler extends RpmBasedHandler {
    @Override
    public default String alias() {
        return "user";
    }
    
    Map<String, Object> create(Map<String, Object> input)
        throws CampaignException;
}