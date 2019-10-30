package com.kencorhealth.campaign.cdn;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CDNUtil {
    private static final String BUCKET_NAME = "com.kencorhealth.campaign";
    private static AmazonS3 s3Client;
    
    public static String makeUrl(String campaignId, String fileId) {
        return
            s3Client
                .getUrl(BUCKET_NAME, campaignId + "/" + fileId)
                .toExternalForm();
    }
    
    public static String tmpDir() {
        return System.getProperty("java.io.tmpdir");
    }
    
    public static File tmpFile(String name) {
        return new File(tmpDir(), name);
    }
    
    public static String upload(String campaignId, File file)
        throws CampaignException {
        String fileName = file.getName();

        PutObjectRequest por =
            new PutObjectRequest(
                BUCKET_NAME,
                campaignId + "/" + fileName,
                file
            );
        
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        por.setAccessControlList(acl);        
        
        s3Client.putObject(por);
        
        return makeUrl(campaignId, fileName);
    }

    public static File download(String fileId) throws CampaignException {
        return download(null, fileId);
    }
    
    public static File download(String campaignId, String fileId)
        throws CampaignException {
        File retVal = null;
        
        String entityName =
            CampaignUtil.valid(campaignId) ? campaignId + "/" + fileId : fileId;
        
        S3Object entity =
            s3Client.getObject(BUCKET_NAME, entityName);
        S3ObjectInputStream inputStream = entity.getObjectContent();
        try {
            retVal = new File(tmpDir(), fileId);
            
            if (retVal.exists()) {
                retVal.delete();
            }
            
            Files.copy(inputStream, retVal.toPath());
        } catch (IOException ex) {
            throw new CampaignException(ex);
        }
        
        return retVal;
    }
    
    public static void init(S3Config config) {
        AWSCredentials credentials = new BasicAWSCredentials(
            config.getAccessKey(), 
            config.getSecretKey()
        );        
        s3Client =
            AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.fromName(config.getRegion()))
                .build();
    }
}
