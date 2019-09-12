package com.kencorhealth.campaign.db.handler;

import com.kencorhealth.campaign.dm.annotations.Exportable;
import com.kencorhealth.campaign.dm.input.AuthInput;
import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.DbException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;

@Exportable
public interface AuthTokenHandler
    extends CampaignBasedHandler<AuthToken, AuthInput> {
    @Override
    default String collectionName() {
        return AUTH_TOKEN_COLLECTION;
    }
    
    AuthToken signIn(AuthInput input)
        throws NotFoundException, DbException, CampaignException;
    
    void signOut(String jwt)
        throws NotFoundException, DbException, CampaignException;

    AuthToken validate(String jwtToken)
        throws NotFoundException, CampaignException;
}
