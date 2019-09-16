package com.kencorhealth.campaign.service.api.campaign.nav;

import com.kencorhealth.campaign.cdn.CDNUtil;
import com.kencorhealth.campaign.db.CampaignFactory;
import com.kencorhealth.campaign.db.CampaignMongoConstants;
import com.kencorhealth.campaign.db.handler.WorkflowDataHandler;
import com.kencorhealth.campaign.dm.auth.AuthToken;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.service.api.CampaignBasedResource;
import io.dropwizard.auth.Auth;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

public class FileResource extends CampaignBasedResource
    implements CampaignMongoConstants {
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(
        @Auth AuthToken at,
        @QueryParam(CONTAINER_ID) String containerId,
        @PathParam(CAMPAIGN_ID) String campaignId,
        @PathParam(NAV_ID) String navId) {
        Response retVal = null;
        
        try (WorkflowDataHandler wdh =
             CampaignFactory.get(WorkflowDataHandler.class)) {
            Map<String, Object> data =
                wdh.get(
                    at.getProviderId(),
                    campaignId,
                    containerId,
                    navId
                );

            String fileId = (String) data.get(FILE_ID_KEY);

            File file = CDNUtil.download(campaignId, fileId);
            
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
    
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(
        FormDataMultiPart form,
        @Auth AuthToken at,
        @QueryParam(CONTAINER_ID) String containerId,
        @PathParam(CAMPAIGN_ID) String campaignId,
        @PathParam(NAV_ID) String navId) {
        Response retVal = null;
        
        FormDataBodyPart filePart = form.getField(FILE);
        InputStream is = filePart.getValueAs(InputStream.class);
        MediaType mediaType = filePart.getMediaType();

        try (WorkflowDataHandler wdh =
             CampaignFactory.get(WorkflowDataHandler.class)) {
            File outFile = doUploadFile(campaignId, is, mediaType);
            
            Map<String, Object> data = new HashMap();
            data.put(FILE_ID_KEY, outFile.getName());
            
            wdh.update(
                at.getProviderId(),
                campaignId,
                containerId,
                navId,
                data
            );
            
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
    
    private File doUploadFile(
        String campaignId, InputStream is, MediaType mediaType)
        throws CampaignException {
        String tmpDir = CDNUtil.tmpDir();
        
        String type = mediaType.getType();
        String subtype = mediaType.getSubtype();
        
        String extension = null;
        
        boolean isBase64 = false;
        
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
            case "application":
                switch (subtype) {
                    case "base64":
                        isBase64 = true;
                        extension = "base64";
                        break;
                }
                break;
        }
        
        File retVal =
            new File(tmpDir, CampaignUtil.uniqueString() + "." + extension);

        try (OutputStream os = isBase64?
             new ByteArrayOutputStream() : new FileOutputStream(retVal)) {
            IOUtils.copy(is, os);
            
            if (isBase64) {
                os.close();
                ByteArrayOutputStream baos = (ByteArrayOutputStream) os;
                String base64 = baos.toString("UTF-8");
                byte[] bytes = CampaignUtil.base64Decode(base64).getBytes();
                
                // Base64 will be converted to png
                retVal = new File(tmpDir, CampaignUtil.uniqueString() + ".png");
                try (FileOutputStream fos = new FileOutputStream(retVal);
                     ByteArrayInputStream bais =
                     new ByteArrayInputStream(bytes)) {
                    IOUtils.copy(bais, fos);
                }
            }
            
        } catch (Exception e) {
            throw new CampaignException(e);
        }
        
        CDNUtil.upload(campaignId, retVal);
        
        return retVal;
    }    
}
