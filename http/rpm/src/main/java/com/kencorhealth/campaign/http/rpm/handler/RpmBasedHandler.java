package com.kencorhealth.campaign.http.rpm.handler;

import com.kencorhealth.campaign.dm.common.AliasProvider;
import com.kencorhealth.campaign.dm.config.RouterConfig;
import com.kencorhealth.campaign.http.base.handler.HttpBasedHandler;
import com.kencorhealth.campaign.http.rpm.RpmConstants;

public interface RpmBasedHandler
    extends HttpBasedHandler, AliasProvider, RpmConstants {
    void setRouterConfig(RouterConfig router);
}
