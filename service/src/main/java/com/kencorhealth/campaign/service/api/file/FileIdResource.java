package com.kencorhealth.campaign.service.api.file;

import com.kencorhealth.campaign.cdn.CDNUtil;
import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import com.kencorhealth.campaign.service.api.auth.PostAuthenticator;
import java.io.File;
import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class FileIdResource extends CampaignBasedResource {
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(
        @QueryParam (AUTHORIZATION) String authorization,
        @PathParam(FILE_ID) String fileId) {
        Response retVal = null;
        
        try {
            PostAuthenticator.doAuthenticate(authorization);
            
            File file = CDNUtil.download(fileId);
            
            retVal =
                Response
                    .ok(file, new MimetypesFileTypeMap().getContentType(file))
                    .header("content-disposition",
                            "attachment;filename=" + file.getName())
                    .build();

        } catch (Exception e) {
            retVal = fromException(e);
        }
        
        return retVal;
    }
}
