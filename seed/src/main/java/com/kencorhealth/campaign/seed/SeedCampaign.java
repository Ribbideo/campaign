package com.kencorhealth.campaign.seed;

import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.Identified;

public class SeedCampaign {
    public static void main(String[] args) throws Exception {
        SeedEnrolment.create();
        
        /*Identified identified = new Identified() {
        };
        
        identified.setId("3923dcfa-7735-4ea1-a38a-3bd5ccc1e1bc");
        identified.setCreateTime(1565279369531L);
        
        String encrypted = CampaignUtil.crypter().encrypt("123456", identified);
        
        System.out.println(encrypted);
        
        String decrypted = CampaignUtil.crypter().decrypt(encrypted, identified);

        System.out.println(decrypted);*/
    }
}
