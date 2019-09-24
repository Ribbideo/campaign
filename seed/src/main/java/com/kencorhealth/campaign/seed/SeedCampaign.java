package com.kencorhealth.campaign.seed;

import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.Identified;

public class SeedCampaign {
    public static void main(String[] args) throws Exception {
        //SeedEnrolment.create();
        
        Identified identified = new Identified() {
        };
        
        identified.setId("4f64fbd3-ccb7-4df0-a319-b5b55415d550");
        identified.setCreateTime(1565603693133L);
        
        String encrypted = CampaignUtil.crypter().encrypt("123456", identified);
        
        System.out.println(encrypted);
        
        String decrypted = CampaignUtil.crypter().decrypt(encrypted, identified);

        System.out.println(decrypted);
    }
}
