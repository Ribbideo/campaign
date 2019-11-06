package com.kencorhealth.campaign.http.rpm.handler.impl;

import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.config.RouterConfig;
import com.kencorhealth.campaign.http.base.handler.impl.HttpBasedHandlerImpl;
import com.kencorhealth.campaign.http.rpm.handler.RpmBasedHandler;

abstract class RpmBasedHandlerImpl extends HttpBasedHandlerImpl
    implements RpmBasedHandler {
    protected RouterConfig router;

    @Override
    public void setAuthorization(String authToken) {
        String tokenToSet = null;
        if (CampaignUtil.valid(authToken)) {
            tokenToSet =
                authToken.startsWith("Bearer ") ?
                authToken : "Bearer " + authToken;
        }

        super.setAuthorization(tokenToSet);
    }
    
    @Override
    public void setRouterConfig(RouterConfig router) {
        this.router = router;
    }
}
