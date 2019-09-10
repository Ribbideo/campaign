package com.kencorhealth.campaign.http.rpm.handler.impl;

import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.http.base.handler.impl.HttpBasedHandlerImpl;
import com.kencorhealth.campaign.http.rpm.UrlInfo;
import com.kencorhealth.campaign.http.rpm.handler.RpmBasedHandler;
import com.kencorhealth.campaign.json.JsonUtil;
import java.util.HashMap;
import java.util.Map;

abstract class RpmBasedHandlerImpl extends HttpBasedHandlerImpl
    implements RpmBasedHandler {
    @Override
    public void setUrlInfo(UrlInfo urlInfo) throws CampaignException {
        setAuthorization(getAuthToken(urlInfo));
    }
    
    protected String getAuthToken(UrlInfo urlInfo) throws CampaignException  {
        Map<String, Object> result = doSignIn(urlInfo);
        
        return (String) result.get("authToken");
    }

    protected Map<String, Object> doSignIn(UrlInfo urlInfo)
        throws CampaignException  {
        Map<String, Object> input = new HashMap();
        
        input.put("mobileNumber", urlInfo.getUserName());
        input.put("password", urlInfo.getPassword());
        
        return
            sendPut(
                null,
                input,
                JsonUtil.mapType(String.class, Object.class),
                V2,
                SIGN_IN
            );
    }
}
