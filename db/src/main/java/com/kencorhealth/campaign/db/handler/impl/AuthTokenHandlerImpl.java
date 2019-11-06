package com.kencorhealth.campaign.db.handler.impl;

import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.DbException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.mongo.handler.impl.MongoHandlerImpl;
import com.mongodb.MongoClient;
import com.kencorhealth.campaign.db.handler.AuthTokenHandler;
import com.kencorhealth.campaign.dm.input.AuthInput;
import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.dm.auth.Identity;
import com.kencorhealth.campaign.dm.exception.ExistsException;
import com.kencorhealth.campaign.dm.rpm.RpmInfo;
import com.kencorhealth.campaign.http.rpm.RpmFactory;
import com.kencorhealth.campaign.http.rpm.handler.RpmHandler;
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
        
        RpmInfo rpmInfo = null;
        String userId = null;
        
        try (RpmHandler handler = RpmFactory.get(RpmHandler.class)) {
            rpmInfo = handler.signIn(input);
            userId = rpmInfo.userId();
        } catch (Exception e) {
            throw new NotFoundException(e);
        }
        
        Map<String, Object> filter = new HashMap();
        filter.put(USER_ID_KEY, rpmInfo.userId());
        
        List<AuthToken> tokens = findMany(filter);

        if (tokens.isEmpty()) {
            retVal = input.convert();
            retVal.setRpm(rpmInfo);
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
            retVal.setRpm(rpmInfo);
            retVal.setJwt(jwt);
            doUpdate(retVal);
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
