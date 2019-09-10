package com.kencorhealth.campaign.dm.provider;

import com.kencorhealth.campaign.dm.common.Described;
import com.kencorhealth.campaign.dm.common.Address;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.output.ProviderDetail;

public class Provider extends Described {
    private String clinicId;
    private String baseUrl;
    private String authToken;
    private ProviderType providerType;
    private Address address;
    
    public ProviderDetail toDetail() {
        ProviderDetail retVal = new ProviderDetail();
        retVal.fillFrom(this);
        
        retVal.setClinicId(clinicId);
        retVal.setAddress(address);
        retVal.setProviderType(providerType);
        
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
            "Provider{" + "baseUrl=" + baseUrl + ", authToken=" +
            CampaignUtil.protect(authToken) +
            ", providerType=" + providerType + ", address=" + address +
            ", clinicId=" + clinicId + "}, " + super.toString();
    }
}
