package com.kencorhealth.campaign.dm.common;

public interface ConvertSupplier {
    default String supplyId() { return CampaignUtil.uniqueString(); }
    default Long supplyCurrentTime() { return CampaignUtil.utcTime(); } 
}
