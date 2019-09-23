package com.kencorhealth.campaign.dm.entity;

import com.kencorhealth.campaign.dm.common.TypeInfo;
import com.kencorhealth.campaign.dm.common.Described;
import com.kencorhealth.campaign.dm.common.CampaignSettings;

public class Campaign extends Described {
    private String goal;
    private String providerId;
    private TypeInfo typeInfo;
    private Long beginDate;
    private Long endDate;
    private CampaignSettings settings;
    private Delivery delivery;
    private String logoUrl;
    private String poweredByUrl;

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getPoweredByUrl() {
        return poweredByUrl;
    }

    public void setPoweredByUrl(String poweredByUrl) {
        this.poweredByUrl = poweredByUrl;
    }
    
    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
    
    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }
    
    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }
    
    public CampaignSettings getSettings() {
        return settings;
    }

    public void setSettings(CampaignSettings settings) {
        this.settings = settings;
    }

    public Long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Long beginDate) {
        this.beginDate = beginDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }
    
    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Override
    public String toString() {
        return
            "Campaign{" + "providerId=" + providerId +
            ", beginDate=" + beginDate + ", endDate=" +
            endDate + ", settings=" + settings + ", typeInfo=" +
            typeInfo + ", goal=" + goal + ", delivery=" + delivery +
            ", logoUrl=" + logoUrl + ", poweredByUrl=" + poweredByUrl +
            "}, " + super.toString();
    }
}
