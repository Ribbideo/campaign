package com.kencorhealth.campaign.http.rpm.handler.impl;

import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.http.rpm.handler.UserHandler;
import com.kencorhealth.campaign.json.JsonUtil;
import java.util.HashMap;
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

    @Override
    public boolean updateConsentFormUrl(String userId, String consentFormUrl)
        throws CampaignException {
        Map<String, Object> body = new HashMap();
        body.put(CONSENT_FORM_URL, consentFormUrl);
        
        return
            sendPut(
                null,
                body,
                JsonUtil.simpleType(Boolean.class),
                V2,
                USER,
                userId
            );
    }

    @Override
    public Map<String, Object> userIfExists(String phoneNumber)
        throws CampaignException {
        Map<String, String> params = new HashMap();
        params.put(PHONE_NUMBER, phoneNumber);
        
        return
            sendGet(params,
                JsonUtil.mapType(String.class, Object.class),
                V2,
                USER,
                USER_IF_EXISTS
            );
    }
}
