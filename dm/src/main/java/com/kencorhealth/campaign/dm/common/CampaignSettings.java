package com.kencorhealth.campaign.dm.common;

public class CampaignSettings {
    private ItemSchedule smsSchedule;
    private ItemSchedule ivrSchedule;

    public ItemSchedule getSmsSchedule() {
        return smsSchedule;
    }

    public void setSmsSchedule(ItemSchedule smsSchedule) {
        this.smsSchedule = smsSchedule;
    }

    public ItemSchedule getIvrSchedule() {
        return ivrSchedule;
    }

    public void setIvrSchedule(ItemSchedule ivrSchedule) {
        this.ivrSchedule = ivrSchedule;
    }

    @Override
    public String toString() {
        return
            "CampaignSettings{" + "smsSchedule=" + smsSchedule +
            ", ivrSchedule=" + ivrSchedule + '}';
    }
}
