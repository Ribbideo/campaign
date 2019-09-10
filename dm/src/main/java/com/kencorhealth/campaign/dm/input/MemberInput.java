package com.kencorhealth.campaign.dm.input;

import com.kencorhealth.campaign.dm.common.Address;
import com.kencorhealth.campaign.dm.common.Gender;
import com.kencorhealth.campaign.dm.common.Input;
import com.kencorhealth.campaign.dm.common.NotifSettings;
import com.kencorhealth.campaign.dm.provider.Member;
import com.ribbideo.dm.input.register.PhoneNumber;
import java.util.Set;

public class MemberInput extends Input<Member> {
    private String providerId;
    private String firstName;
    private String lastName;
    private String landline;
    private String mobileNumber;
    private NotifSettings notifSettings;
    private String email;
    private Gender gender;
    private Long dob;
    private Address location;
    private Set<String> tags;

    public MemberInput() {
        super();
    }
    
    public MemberInput(com.ribbideo.dm.input.register.Patient patient) {
        firstName = patient.getFirstName();
        lastName = patient.getLastName();
        
        PhoneNumber.PhoneNumberType phoneType = patient.getPhoneType();
        
        if (phoneType == null) {
            mobileNumber = patient.getMobileNumber();
        } else {
            switch (phoneType) {
                case mobile:
                case home:
                    mobileNumber = patient.getMobileNumber();
                    break;
                case landline:
                    landline = patient.getMobileNumber();
                    break;
            }
        }
        
        email = patient.getEmail();
        gender = Gender.from(patient.getGender());
        dob = patient.getDob();
        
        com.ribbideo.dm.shared.Address address = patient.getAddress();
        
        if (address != null) {
            location = new Address(address);
        }
    }
    
    @Override
    public Member convert() {
        Member retVal = new Member();

        retVal.autoFill();
        
        retVal.setProviderId(providerId);
        retVal.setFirstName(firstName);
        retVal.setLastName(lastName);
        retVal.setLandLine(landline);
        retVal.setMobileNumber(mobileNumber);
        retVal.setEmail(email);
        retVal.setGender(gender);
        retVal.setNotifSettings(notifSettings);
        retVal.setDob(dob);
        retVal.setLocation(location);
        retVal.setTags(tags);
        
        return retVal;
    }

    public NotifSettings getNotifSettings() {
        return notifSettings;
    }

    public void setNotifSettings(NotifSettings notifSettings) {
        this.notifSettings = notifSettings;
    }
    
    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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
            "MemberInput{" + "providerId=" + providerId + ", firstName=" +
            firstName + ", lastName=" + lastName + ", landline=" + landline +
            ", mobileNumber=" + mobileNumber + ", email=" + email +
            ", gender=" + gender + ", dob=" + dob + ", location=" +
            location + ", tags=" + tags + ", notifSettings=" +
            notifSettings + '}';
    }
}
