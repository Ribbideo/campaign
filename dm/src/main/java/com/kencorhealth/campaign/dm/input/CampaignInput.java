package com.kencorhealth.campaign.dm.input;

import com.kencorhealth.campaign.dm.common.CampaignSettings;
import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.dm.common.TypeInfo;
import com.kencorhealth.campaign.dm.entity.Delivery;

public class CampaignInput extends DescribedInput<Campaign> {
    private String providerId;
    private Long beginDate;
    private Long endDate;
    private String goal;
    private TypeInfo typeInfo;
    private Delivery delivery;
    private CampaignSettings settings;
    private String logoUrl;
    private String poweredByUrl;

    @Override
    public Campaign convert() {
        Campaign retVal = new Campaign();
        
        doConvert(retVal);
        
        retVal.setGoal(goal);
        retVal.setTypeInfo(typeInfo);
        retVal.setDelivery(delivery);
        retVal.setProviderId(providerId);
        retVal.setBeginDate(beginDate);
        retVal.setEndDate(endDate);
        retVal.setSettings(settings);
        retVal.setLogoUrl(logoUrl);
        retVal.setPoweredByUrl(poweredByUrl);
        
        return retVal;
    }

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
    
    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
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
            "Campaign{" + "providerId=" + providerId + ", beginDate=" +
            beginDate + ", endDate=" + endDate + ", settings=" + settings +
            ", delivery=" + delivery + ", goal=" + goal +
            ", typeInfo=" + typeInfo + ", logoUrl=" + logoUrl +
            ", poweredByUrl=" + poweredByUrl + "}, " + super.toString();
    }
}
