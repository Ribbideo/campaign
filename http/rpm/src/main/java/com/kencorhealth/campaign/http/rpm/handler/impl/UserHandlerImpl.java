package com.kencorhealth.campaign.http.rpm.handler.impl;

import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.http.rpm.handler.UserHandler;
import com.kencorhealth.campaign.json.JsonUtil;
import java.util.Map;

public class UserHandlerImpl extends RpmBasedHandlerImpl
    implements UserHandler {
    @Override
    public Map<String, Object> create(Map<String, Object> input)
        throws CampaignException {
        return
            sendPost(
                null,
                input,
                JsonUtil.mapType(String.class, Object.class),
                V2,
                USER
            );
    }
}
