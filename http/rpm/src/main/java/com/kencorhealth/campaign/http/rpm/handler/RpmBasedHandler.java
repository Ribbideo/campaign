package com.kencorhealth.campaign.http.rpm.handler;

import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.http.base.handler.HttpBasedHandler;
import com.kencorhealth.campaign.http.rpm.RpmConstants;
import com.kencorhealth.campaign.http.rpm.UrlInfo;

public interface RpmBasedHandler extends HttpBasedHandler, RpmConstants {
    String alias();
    void setUrlInfo(UrlInfo urlInfo) throws CampaignException;
}
