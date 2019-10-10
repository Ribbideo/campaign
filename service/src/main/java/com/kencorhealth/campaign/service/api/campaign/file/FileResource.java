package com.kencorhealth.campaign.service.api.campaign.file;

import com.kencorhealth.campaign.cdn.CDNUtil;
import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import io.dropwizard.auth.Auth;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

public class FileResource extends CampaignBasedResource {
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(
        FormDataMultiPart form,
        @Auth AuthToken at,
        @PathParam(CAMPAIGN_ID) String campaignId) {
        Response retVal = null;
        
        FormDataBodyPart filePart = form.getField(FILE);
        InputStream is = filePart.getValueAs(InputStream.class);
        MediaType mediaType = filePart.getMediaType();

        try {
            File outFile = doUploadFile(campaignId, is, mediaType);
            
            retVal =
                Response
                    .status(HttpServletResponse.SC_CREATED)
                    .entity(outFile.getName())
                    .build();
        } catch (Exception e) {
            retVal = fromException(e);
        }
        
        return retVal;
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadEncodedFile(
        String encodedContents,
        @Auth AuthToken at,
        @PathParam(CAMPAIGN_ID) String campaignId) {
        Response retVal = null;
        
        try {
            File outFile = uploadEncodedContents(campaignId, encodedContents);
            
            retVal =
                Response
                    .status(HttpServletResponse.SC_CREATED)
                    .entity(outFile.getName())
                    .build();
        } catch (Exception e) {
            retVal = fromException(e);
        }
        
        return retVal;
    }
    
    @Path("/" + FILE_ID_ENDPOINT)
    public FileIdResource getFileIdResource() {
        return new FileIdResource();
    }

    private File doUploadFile(
        String campaignId, InputStream is, MediaType mediaType)
        throws CampaignException {
        String tmpDir = CDNUtil.tmpDir();
        
        String type = mediaType.getType();
        String subtype = mediaType.getSubtype();
        
        String extension = null;
        
        switch (type) {
            case "image":
                switch (subtype) {
                    case "png":
                        extension = "png";
                        break;
                    case "gif":
                        extension = "gif";
                        break;
                    case "jpeg":
                    case "jpg":
                        extension = "jpg";
                        break;
                }
                break;
        }
        
        File retVal =
            new File(tmpDir, CampaignUtil.uniqueString() + "." + extension);

        try (OutputStream os = new FileOutputStream(retVal)) {
            IOUtils.copy(is, os);
        } catch (Exception e) {
            throw new CampaignException(e);
        }
        
        CDNUtil.upload(campaignId, retVal);
        
        return retVal;
    }
    
    private File uploadEncodedContents(String campaignId, String base64)
        throws FileNotFoundException, IOException, CampaignException {
        byte[] bytes = CampaignUtil.base64Decode(base64).getBytes();

        String tmpDir = CDNUtil.tmpDir();

        // Base64 will be converted to png
        File retVal = new File(tmpDir, CampaignUtil.uniqueString() + ".png");
        try (FileOutputStream fos = new FileOutputStream(retVal);
             ByteArrayInputStream bais =
             new ByteArrayInputStream(bytes)) {
            IOUtils.copy(bais, fos);
        }
        
        CDNUtil.upload(campaignId, retVal);
        
        return retVal;
    }
}
