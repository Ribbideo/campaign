package com.kencorhealth.campaign.dm.input;

import com.kencorhealth.campaign.dm.common.Address;
import com.kencorhealth.campaign.dm.common.Gender;
import com.kencorhealth.campaign.dm.common.Input;
import com.kencorhealth.campaign.dm.common.NotifSettings;
import com.kencorhealth.campaign.dm.common.PhoneType;
import com.kencorhealth.campaign.dm.clinic.Member;
import com.kencorhealth.campaign.dm.clinic.RoleInfo;
import java.util.Set;

public class MemberInput extends Input<Member> {
    private String consentFormUrl;
    private String roleName;
    private String clinicId;
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

    @Override
    public Member convert() {
        Member retVal = new Member();

        retVal.autoFill();
        
        retVal.setConsentFormUrl(consentFormUrl);
        retVal.setClinicId(clinicId);
        retVal.setFirstName(firstName);
        retVal.setLastName(lastName);
        retVal.setPhoneNumber(phoneNumber);
        retVal.setPhoneType(phoneType);
        retVal.setEmail(email);
        retVal.setGender(gender);
        retVal.setNotifSettings(notifSettings);
        retVal.setDob(dob);
        retVal.setLocation(location);
        retVal.setTags(tags);
        retVal.setRoleInfo(RoleInfo.from(roleName));
        
        return retVal;
    }

    public String getConsentFormUrl() {
        return consentFormUrl;
    }

    public void setConsentFormUrl(String consentFormUrl) {
        this.consentFormUrl = consentFormUrl;
    }
    
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    public NotifSettings getNotifSettings() {
        return notifSettings;
    }

    public void setNotifSettings(NotifSettings notifSettings) {
        this.notifSettings = notifSettings;
    }

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
    
    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
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
            "MemberInput{" + "clinicId=" + clinicId + ", firstName=" +
            firstName + ", lastName=" + lastName + ", phoneNumber=" +
            phoneNumber + ", phoneType=" + phoneType + ", email=" + email +
            ", gender=" + gender + ", dob=" + dob + ", location=" + location +
            ", tags=" + tags + ", notifSettings=" + notifSettings +
            ", roleName=" + roleName + ", approverId=" + ", consentFormUrl=" +
            consentFormUrl + '}';
    }
}
