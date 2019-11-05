package com.kencorhealth.campaign.db.handler.impl;

import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.DbException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.mongo.handler.impl.MongoHandlerImpl;
import com.mongodb.MongoClient;
import com.kencorhealth.campaign.db.handler.AuthTokenHandler;
import com.kencorhealth.campaign.db.handler.MemberHandler;
import com.kencorhealth.campaign.dm.input.AuthInput;
import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.dm.auth.Identity;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.exception.ExistsException;
import com.kencorhealth.campaign.dm.provider.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthTokenHandlerImpl
    extends MongoHandlerImpl<AuthToken, AuthInput>
    implements AuthTokenHandler {
    public AuthTokenHandlerImpl(MongoClient mc) {
        super(mc);
    }

    @Override
    public AuthToken signIn(AuthInput input)
        throws NotFoundException, DbException, CampaignException {
        AuthToken retVal = null;
        
        Identity identity = input.getIdentity();
        
        String key = null;
        
        switch (identity.getType()) {
            case EMAIL:
                key = EMAIL_KEY;
                break;
            case MOBILE_NUMBER:
                key = PHONE_NUMBER_KEY;
                break;
        }
        
        Map<String, Object> filter = new HashMap();
        filter.put(key, identity.getValue());
        filter.put(ROLE_INFO_KEY + "." + ROLE_KEY, input.getRole());
        
        try (MemberHandler mh = new MemberHandlerImpl(mc)) {
            List<Member> members = mh.findMany(filter);
            
            if (!members.isEmpty()) {
                Member m = members.get(0);
                
                Map<String, Object> extra = m.getExtra();
                
                String password = (String) extra.get(PASSWORD_KEY);
                
                if (CampaignUtil.verify(input.getPassword(), password, m)) {
                    String providerId = m.getProviderId();
                    String userId = m.getId();
                    
                    filter.clear();
                    filter.put(PROVIDER_ID_KEY, providerId);
                    filter.put(USER_ID_KEY, userId);
                    
                    List<AuthToken> tokens = findMany(filter);
                    
                    if (tokens.isEmpty()) {
                        retVal = input.convert();
                        retVal.setProviderId(m.getProviderId());
                        retVal.setApproverId(m.getApproverId());
                        retVal.setApproverName(m.getApproverName());
                        retVal.setUserId(m.getId());
                        String jwt = JWTUtil.create(userId, retVal.getId(), 30);
                        retVal.setJwt(jwt);

                        try {
                            doAdd(retVal);
                        } catch (ExistsException ex) {
                            // Will not happen
                            ex.printStackTrace();
                        }
                    } else {
                        retVal = tokens.get(0);
                        String jwt = JWTUtil.create(userId, retVal.getId(), 30);
                        retVal.setProviderId(m.getProviderId());
                        retVal.setApproverId(m.getApproverId());
                        retVal.setApproverName(m.getApproverName());
                        retVal.setUserId(m.getId());
                        retVal.setJwt(jwt);
                        doUpdate(retVal);
                    }
                }
            } else {
                throw new NotFoundException("Entry not found");
            }
        }
        
        return retVal;
    }

    @Override
    public void signOut(String jwt)
        throws NotFoundException, DbException, CampaignException {
        Map<String, Object> filter = new HashMap();
        filter.put(JWT_KEY, jwt);
                    
        List<AuthToken> tokens = findMany(filter);

        if (!tokens.isEmpty()) {
            AuthToken at = tokens.get(0);
            at.setJwt(null);
            doUpdate(at);
        } else {
            throw new NotFoundException("Entry not found");
        }
    }

    @Override
    public AuthToken validate(String jwt)
        throws NotFoundException, CampaignException {
        AuthToken retVal = null;
        
        Map<String, Object> filter = new HashMap();
        filter.put(JWT_KEY, jwt);
        
        List<AuthToken> tokens = findMany(filter);
        
        if (!tokens.isEmpty()) {
            retVal = tokens.get(0);
        } else {
            throw new NotFoundException("Token not found");
        }
        
        return retVal;
    }
}
