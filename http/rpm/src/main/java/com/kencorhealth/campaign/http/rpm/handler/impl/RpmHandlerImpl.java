package com.kencorhealth.campaign.http.rpm.handler.impl;

import com.kencorhealth.campaign.dm.rpm.RpmInfo;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.input.AuthInput;
import com.kencorhealth.campaign.dm.rpm.ClinicInfo;
import com.kencorhealth.campaign.dm.rpm.ClinicType;
import com.kencorhealth.campaign.dm.rpm.RouterInfo;
import com.kencorhealth.campaign.dm.rpm.RouterResponse;
import com.kencorhealth.campaign.json.JsonUtil;
import java.util.HashMap;
import java.util.Map;
import com.kencorhealth.campaign.http.rpm.handler.RpmHandler;

public class RpmHandlerImpl extends RpmBasedHandlerImpl
    implements RpmHandler {
    @Override
    public Map<String, Object> create(Map<String, Object> input)
        throws CampaignException {
        return
            sendPost(
                null,
                input,
                JsonUtil.mapType(String.class, Object.class),
                V2,
                USER
            );
    }

    @Override
    public ClinicInfo getClinic(String clinicId) throws CampaignException {
        Map<String, Object> response =
            sendGet(
                null,
                JsonUtil.mapType(String.class, Object.class),
                V1,
                CLINIC,
                clinicId
            );

        Map<String, Object> clinicDetail =
            (Map<String, Object>) response.get(CLINIC_DETAIL_KEY);
        
	ClinicType clinicType =
            ClinicType.valueOf((String) clinicDetail.get(CLINIC_TYPE_KEY));
        String name = (String) clinicDetail.get(NAME_KEY);
        String description = (String) clinicDetail.get(DESCRIPTION_KEY);
        String logoUrl = (String) clinicDetail.get(LOGO_URL_KEY);
        String videoUrl = (String) clinicDetail.get(VIDEO_URL_KEY);

        ClinicInfo retVal = new ClinicInfo();
        
        retVal.setId(clinicId);
        retVal.setDescription(description);
        retVal.setType(clinicType);
        retVal.setLogoUrl(logoUrl);
        retVal.setVideoUrl(videoUrl);
        retVal.setName(name);
        
        return retVal;
    }
    
    @Override
    public RpmInfo signIn(AuthInput input) throws CampaignException {
        RouterInfo routerInfo = getRouterInfo(input.getIdentity().getValue());
        
        String baseUrl = routerInfo.rpmUrl();
        
        setBaseUrl(baseUrl);
        
        Map<String, Object> jsonBody = new HashMap();
        jsonBody.put("token", input.getPassword());
        jsonBody.put("accountType", 1);
        jsonBody.put("mobileNumber", input.getIdentity().getValue());
        
        Map<String, Object> response =
            sendPut(
                null,
                jsonBody,
                JsonUtil.mapType(String.class, Object.class),
                V2,
                USER,
                SIGN_IN
            );
        
        Map<String, Object> authOutput =
            (Map<String, Object>) response.get(AUTH_OUTPUT_KEY);
        String authToken = (String) authOutput.get(AUTH_TOKEN_KEY);
        String userJson = (String) authOutput.get(USER_JSON_KEY);
        
        RpmInfo retVal = new RpmInfo();
        retVal.setAuthToken(authToken);

        Map<String, Object> user =
            JsonUtil.asObject(
                userJson,
                JsonUtil.mapType(String.class, Object.class)
            );

        retVal.setUser(user);
        retVal.setBaseUrl(baseUrl);
        
        setAuthorization(authToken);
        
        // Now fetch clinic
        ClinicInfo clinic = getClinic((String) user.get(CLINIC_ID_KEY));
        
        retVal.setClinic(clinic);
        
        return retVal;
    }

    @Override
    public boolean updateConsentFormUrl(String userId, String consentFormUrl)
        throws CampaignException {
        Map<String, Object> body = new HashMap();
        body.put(CONSENT_FORM_URL_KEY, consentFormUrl);
        body.put(STATE_KEY, "invite_sent");
        
        return
            sendPut(
                null,
                body,
                JsonUtil.simpleType(Boolean.class),
                V2,
                USER,
                userId
            );
    }

    @Override
    public Map<String, Object> userIfExists(String phoneNumber)
        throws CampaignException {
        Map<String, String> params = new HashMap();
        params.put(PHONE_NUMBER_KEY, phoneNumber);
        
        return
            sendGet(params,
                JsonUtil.mapType(String.class, Object.class),
                V2,
                USER,
                USER_IF_EXISTS
            );
    }

    private RouterInfo getRouterInfo(String mobileNumber)
        throws CampaignException {
        String authorization = getAuthorization();
        String baseUrl = getBaseUrl();
        
        setAuthorization(router.getToken());
        setBaseUrl(router.getUrl());
        
        RouterResponse response =
            sendGet(
                null,
                JsonUtil.simpleType(RouterResponse.class),
                ENDPOINT,
                mobileNumber
            );
        
        setAuthorization(authorization);
        setBaseUrl(baseUrl);
        
        return response.getData();
    }
}
