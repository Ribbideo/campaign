package com.kencorhealth.campaign.seed;

import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.Identified;

public class SeedCampaign {
    public static void main(String[] args) throws Exception {
        SeedEnrolment.create();
        
        /*Identified identified = new Identified() {
        };
        
        identified.setId("a7be3382-9218-44c5-bc31-4344de76f97e");
        identified.setCreateTime(1565279369531L);
        
        String encrypted = CampaignUtil.crypter().encrypt("123456", identified);
        
        System.out.println(encrypted);
        
        String decrypted = CampaignUtil.crypter().decrypt(encrypted, identified);

        System.out.println(decrypted);*/
    }
}
