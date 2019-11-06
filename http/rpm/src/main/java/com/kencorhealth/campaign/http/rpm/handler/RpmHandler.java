package com.kencorhealth.campaign.http.rpm.handler;

import com.kencorhealth.campaign.dm.annotations.Exportable;
import com.kencorhealth.campaign.dm.rpm.ClinicInfo;
import com.kencorhealth.campaign.dm.rpm.RpmInfo;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.input.AuthInput;
import java.util.Map;

@Exportable
public interface RpmHandler extends RpmBasedHandler {
    @Override
    public default String alias() {
        return "rpm";
    }

    RpmInfo signIn(AuthInput input) throws CampaignException;
    ClinicInfo getClinic(String clinicId) throws CampaignException;
    
    Map<String, Object> userIfExists(String phoneNumber)
        throws CampaignException;
    boolean updateConsentFormUrl(String userId, String consentFormUrl)
        throws CampaignException;
    
    Map<String, Object> create(Map<String, Object> input)
        throws CampaignException;
}
