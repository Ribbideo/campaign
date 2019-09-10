package com.kencorhealth.campaign.dm.common;

public class NotifSettings {
    private NotifType notifType;
    private String preferredTime;

    public NotifType getNotifType() {
        return notifType;
    }

    public void setNotifType(NotifType notifType) {
        this.notifType = notifType;
    }

    public String getPreferredTime() {
        return preferredTime;
    }

    public void setPreferredTime(String preferredTime) {
        this.preferredTime = preferredTime;
    }

    @Override
    public String toString() {
        return
            "NotifSettings{" + "notifType=" + notifType + ", preferredTime=" +
            preferredTime + '}';
    }
}
