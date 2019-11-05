package com.kencorhealth.campaign.dm.provider;

import com.kencorhealth.campaign.dm.common.Address;
import com.kencorhealth.campaign.dm.common.Gender;
import com.kencorhealth.campaign.dm.common.Identified;
import com.kencorhealth.campaign.dm.common.NotifSettings;
import com.kencorhealth.campaign.dm.common.PhoneType;
import java.util.Map;
import java.util.Set;

public class Member extends Identified {
    private String consentFormUrl;
    private String providerId;
    private String approverId;
    private String approverName;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private PhoneType phoneType;
    private NotifSettings notifSettings;
    private String email;
    private Gender gender;
    private Long dob;
    private Address location;
    private Set<String> tags;
    private RoleInfo roleInfo;
    private Map<String, Object> extra;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public String getConsentFormUrl() {
        return consentFormUrl;
    }

    public void setConsentFormUrl(String consentFormUrl) {
        this.consentFormUrl = consentFormUrl;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }
    
    public RoleInfo getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(RoleInfo roleInfo) {
        this.roleInfo = roleInfo;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    public NotifSettings getNotifSettings() {
        return notifSettings;
    }

    public void setNotifSettings(NotifSettings notifSettings) {
        this.notifSettings = notifSettings;
    }
    
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getDob() {
        return dob;
    }

    public void setDob(Long dob) {
        this.dob = dob;
    }

    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
        this.location = location;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
    
    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return
            "Member{" + "providerId=" + providerId + ", firstName=" +
            firstName + ", lastName=" + lastName + ", phoneNumber=" +
            phoneNumber + ", phoneType=" + phoneType + ", email=" + email +
            ", gender=" + gender + ", dob=" + dob + ", location=" + location +
            ", tags=" + tags + ", notifSettings=" + notifSettings +
            ", roleInfo=" + roleInfo + ", extra=" + extra + ", approverId=" +
            approverId + ", approverName=" + approverName +
            ", consentFormUrl=" + consentFormUrl + "}, " + super.toString();
    }
}
