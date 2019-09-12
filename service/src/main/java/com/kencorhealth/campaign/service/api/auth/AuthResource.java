package com.kencorhealth.campaign.service.api.auth;

import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.kencorhealth.campaign.db.handler.AuthTokenHandler;
import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.dm.input.AuthInput;
import io.dropwizard.auth.Auth;

public class AuthResource extends CampaignBasedResource {
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(AuthInput input) {
        Response retVal = null;

        try (AuthTokenHandler ath =
             CampaignFactory.get(AuthTokenHandler.class)) {
            AuthToken at = ath.signIn(input);
            
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(at)
                    .build();
            
        } catch (Exception e) {
            retVal = fromException(e);
        }

        return retVal;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response signOut(@Auth AuthToken at) {
        Response retVal = null;

        try (AuthTokenHandler ath =
             CampaignFactory.get(AuthTokenHandler.class)) {
            ath.signOut(at.getJwt());
            
            retVal =
                Response
                    .status(HttpServletResponse.SC_OK)
                    .entity(true)
                    .build();
            
        } catch (Exception e) {
            retVal = fromException(e);
        }

        return retVal;
    }
}
