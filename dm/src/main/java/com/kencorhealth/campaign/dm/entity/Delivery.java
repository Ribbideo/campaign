package com.kencorhealth.campaign.dm.entity;

import com.kencorhealth.campaign.dm.delivery.type.SmsMethod;
import com.kencorhealth.campaign.dm.delivery.type.WebMethod;

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
