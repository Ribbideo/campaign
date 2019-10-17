package com.kencorhealth.campaign.service.api;

import com.kencorhealth.campaign.dm.exception.CampaignBasedException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.ws.rs.core.Response;
import com.kencorhealth.campaign.service.common.CampaignConstants;
import javax.ws.rs.OPTIONS;

public abstract class CampaignBasedResource implements CampaignConstants {
    protected Response fromException(Exception e) {
        int errorCode = errorCodeFromException(e);
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionStr = sw.toString();
        
        return
            Response
                .status(errorCode, e.getMessage())
                .entity(exceptionStr)
                .build();
    }

    protected int errorCodeFromException(Exception e) {
        int retVal = 500;
        
        if (e instanceof CampaignBasedException) {
            CampaignBasedException cbe = (CampaignBasedException) e;
            retVal = cbe.errorCode();
        }
        
        return retVal;
    }

    @OPTIONS
    public Response handleOptioinsCall(){
      return
          Response
              .ok("OPTIONS")
              .header("Allow", "GET")
              .header("Allow", "PUT")
              .header("Allow", "POST")
              .header("Allow", "DELETE")
              .header("Allow", "OPTIONS")
              .build();
    }
}
