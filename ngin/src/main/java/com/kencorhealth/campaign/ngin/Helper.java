package com.kencorhealth.campaign.ngin;

import com.kencorhealth.campaign.cdn.CDNUtil;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.input.MemberInput;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Helper {
    public MemberInput newMember() {
        return new MemberInput();
    }
    
    public String upload(String campaignId, File file)
        throws CampaignException {
        return CDNUtil.upload(campaignId, file);
    }
    
    public File download(String campaignId, String fileId)
        throws CampaignException {
        return CDNUtil.download(campaignId, fileId);
    }

    public File download(String fileId) throws CampaignException {
        return CDNUtil.download(fileId);
    }
    
    public Map<String, Object> newMap() {
        return new HashMap();
    }
    
    public String today() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(new Date());
    }
}
