package com.kencorhealth.campaign.dm.delivery.sms;

import com.kencorhealth.campaign.dm.delivery.DeliveryMethod;
import com.kencorhealth.campaign.dm.delivery.nav.Nav;
import java.util.List;

public class SmsMethod implements DeliveryMethod {
    private String text;
    private List<Nav> nav;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Nav> getNav() {
        return nav;
    }

    public void setNav(List<Nav> nav) {
        this.nav = nav;
    }

    @Override
    public String toString() {
        return "SmsMethod{" + "text=" + text + ", nav=" + nav + '}';
    }
}
