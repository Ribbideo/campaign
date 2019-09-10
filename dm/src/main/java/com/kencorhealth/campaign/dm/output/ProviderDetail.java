package com.kencorhealth.campaign.dm.output;

import com.kencorhealth.campaign.dm.common.Address;
import com.kencorhealth.campaign.dm.common.Described;
import com.kencorhealth.campaign.dm.provider.Provider;
import com.kencorhealth.campaign.dm.provider.ProviderType;

public class ProviderDetail extends Described {
    private ProviderType providerType;
    private Address address;
    private String clinicId;
    
    public void fillFrom(Provider another) {
        super.fillFrom(another);
        
        clinicId = another.getClinicId();
        providerType = another.getProviderType();
        address = another.getAddress();
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
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
            "ProviderDetail{" + "providerType=" + providerType +
            ", address=" + address + ", clinicId=" + clinicId +
            "}, " + super.toString();
    }
}
