package com.kencorhealth.campaign.seed;

import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.Identified;

public class SeedCampaign {
    public static void main(String[] args) throws Exception {
        SeedEnrolment.create();
        
        Identified identified = new Identified() {
        };
        
        identified.setId("793191c3-46ba-4efc-a343-cfd9a80720e5");
        identified.setCreateTime(1565279369531L);
        
        System.out.println(CampaignUtil.crypter().encrypt("123456", identified));
    }
}
