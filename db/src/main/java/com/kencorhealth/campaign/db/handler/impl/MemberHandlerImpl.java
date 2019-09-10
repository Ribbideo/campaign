package com.kencorhealth.campaign.db.handler.impl;

import com.kencorhealth.campaign.db.handler.MemberHandler;
import com.kencorhealth.campaign.db.handler.ProviderHandler;
import com.kencorhealth.campaign.dm.provider.Member;
import com.kencorhealth.campaign.dm.provider.Provider;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.DbException;
import com.kencorhealth.campaign.dm.exception.ExistsException;
import com.kencorhealth.campaign.dm.exception.InvalidException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.dm.input.MemberInput;
import com.kencorhealth.campaign.mongo.handler.impl.MongoHandlerImpl;
import com.mongodb.MongoClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberHandlerImpl extends MongoHandlerImpl<Member, MemberInput>
    implements MemberHandler {
    public MemberHandlerImpl(MongoClient mc) {
        super(mc);
    }

    @Override
    public String add(MemberInput input)
        throws InvalidException, ExistsException,
        DbException, NotFoundException, CampaignException {
        String retVal = null;
        
        try (ProviderHandler ph = new ProviderHandlerImpl(mc)) {
            String providerId = input.getProviderId();
            
            Provider p = ph.findById(providerId);
            
            if (p == null) {
                String message =
                    "Provider with Id '" + providerId + "' not found";
                throw new InvalidException(message);
            }
            
            String lastName = input.getLastName();
            String firstName = input.getFirstName();

            try {
                findByName(providerId, lastName, firstName);

                String message =
                    "Member for last name '" + lastName +
                    "' and first name '" + firstName + "' already exists";
                
                throw new ExistsException(message);
            } catch (NotFoundException e) {
                retVal = doAdd(input);
            }
        } catch (CampaignException e) {
            throw new DbException(e);
        }
        
        return retVal;
    }

    @Override
    public List<Member> findByProvider(String providerId)
        throws NotFoundException, CampaignException {
        Map<String, Object> filter = new HashMap();
        
        filter.put(PROVIDER_ID_KEY, providerId);
        
        List<Member> retVal = super.findMany(filter);

        if (retVal.isEmpty()) {
            throw new NotFoundException("No data found");
        }
        
        return retVal;
    }

    @Override
    public Member findByName(
        String providerId, String lastName, String firstName)
        throws NotFoundException, CampaignException {
        Member retVal = null;
        
        Map<String, Object> filter = new HashMap();

        filter.put(PROVIDER_ID_KEY, providerId);
        filter.put(LAST_NAME_KEY, lastName);
        filter.put(FIRST_NAME_KEY, firstName);
        
        List<Member> members = findMany(filter);
        
        if (members.isEmpty()) {
            throw new NotFoundException("No data found");
        } else {
            retVal = members.get(0);
        }
        
        return retVal;
    }

    @Override
    public Member findByMobileNumber(
        String providerId, String mobileNumber)
        throws NotFoundException, CampaignException {
        Member retVal = null;
        
        Map<String, Object> filter = new HashMap();

        filter.put(PROVIDER_ID_KEY, providerId);
        filter.put(MOBILE_NUMBER_KEY, mobileNumber);
        
        List<Member> members = findMany(filter);
        
        if (members.isEmpty()) {
            throw new NotFoundException("No data found");
        } else {
            retVal = members.get(0);
        }
        
        return retVal;
    }

    @Override
    public Member findById(String providerId, String memberId)
        throws NotFoundException, CampaignException {
        Member retVal = null;
        
        Map<String, Object> filter = new HashMap();
        
        filter.put(PROVIDER_ID_KEY, providerId);
        filter.put(ID_KEY, memberId);
        
        List<Member> members = super.findMany(filter);

        if (members.isEmpty()) {
            throw new NotFoundException("No data found");
        } else {
            retVal = members.get(0);
        }
        
        return retVal;
    }
}
