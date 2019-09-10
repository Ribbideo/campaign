package com.kencorhealth.campaign.dm.input;

import com.kencorhealth.campaign.dm.common.Address;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.provider.Provider;
import com.kencorhealth.campaign.dm.provider.ProviderType;

public class ProviderInput extends DescribedInput<Provider> {
    private String baseUrl;
    private String authToken;
    private ProviderType providerType;
    private Address address;
    private String clinicId;

    @Override
    public Provider convert() {
        Provider retVal = new Provider();
        
        doConvert(retVal);

        retVal.setClinicId(clinicId);
        retVal.setProviderType(providerType);
        retVal.setAddress(address);
        retVal.setBaseUrl(baseUrl);
        retVal.setAuthToken(authToken);
        
        return retVal;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }
    
    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    
    public ProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return
            "ProviderInput{" + "baseUrl=" + baseUrl + ", authToken=" +
            CampaignUtil.protect(authToken) + ", providerType=" + providerType +
            ", address=" + address + ", clinicId=" + clinicId +
            "}, " + super.toString();
    }
}
