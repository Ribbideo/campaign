package com.kencorhealth.campaign.dm.common;

import com.kencorhealth.campaign.dm.entity.CampaignType;

public class TypeInfo {
    private CampaignType campaignType;
    private String comments;
    
    public static TypeInfo from(CampaignType campaignType) {
        TypeInfo retVal = new TypeInfo();

        retVal.setCampaignType(campaignType);
        retVal.setComments(campaignType.stringify());
        
        return retVal;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public CampaignType getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(CampaignType campaignType) {
        this.campaignType = campaignType;
    }

    @Override
    public String toString() {
        return
            "TypeInfo{" + "campaignType=" + campaignType + ", comments=" +
             comments + '}';
    }
}
