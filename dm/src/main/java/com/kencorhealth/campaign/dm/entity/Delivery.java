package com.kencorhealth.campaign.dm.entity;

import com.kencorhealth.campaign.dm.delivery.sms.SmsMethod;
import com.kencorhealth.campaign.dm.delivery.web.WebMethod;

public class Delivery {
    private SmsMethod sms;
    private WebMethod web;

    public SmsMethod getSms() {
        return sms;
    }

    public void setSms(SmsMethod sms) {
        this.sms = sms;
    }

    public WebMethod getWeb() {
        return web;
    }

    public void setWeb(WebMethod web) {
        this.web = web;
    }

    @Override
    public String toString() {
        return "Delivery{" + "sms=" + sms + ", web=" + web + '}';
    }
}
