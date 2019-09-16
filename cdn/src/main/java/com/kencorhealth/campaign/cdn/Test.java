package com.kencorhealth.campaign.cdn;

import com.kencorhealth.campaign.dm.common.CampaignUtil;
import java.io.File;
import java.nio.file.Files;

public class Test {
    public static void main(String[] args) throws Exception {
        File in = new File("/tmp/signature.png");
        File out = new File("/tmp/signature.base64");
        
        byte[] bytes = Files.readAllBytes(in.toPath());
        String str = new String(bytes, "UTF-8");
        String encoded = CampaignUtil.base64Encode(str);
        
        Files.write(out.toPath(), encoded.getBytes());
    }
}
