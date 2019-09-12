package com.kencorhealth.campaign.service.api.auth;

import com.kencorhealth.campaign.db.handler.impl.JWTUtil;
import com.kencorhealth.campaign.dm.auth.AuthToken;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import java.util.Optional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class PostAuthenticator implements Authenticator<String, AuthToken> {
    @Override
    public Optional<AuthToken> authenticate(String jwt)
        throws AuthenticationException {
        Optional<AuthToken> retVal = null;
        
        try {
            AuthToken at = JWTUtil.validate(jwt);
            retVal = Optional.of(at);
        } catch (Exception ex) {
            int statusCode = Response.Status.UNAUTHORIZED.getStatusCode();
            
            throw new WebApplicationException(ex, statusCode);
        }
        
        return retVal;
    }
}
