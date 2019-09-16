package com.kencorhealth.campaign.cdn;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CDNUtil {
    private static final String BUCKET_NAME = "com.kencorhealth.campaign";
    private static AmazonS3 s3client;
    
    public static String tmpDir() {
        return System.getProperty("java.io.tmpdir");
    }
    
    public static void upload(String campaignId, File file)
        throws CampaignException {
        s3client.putObject(
            BUCKET_NAME,
            campaignId + "/" + file.getName(), file
        );
    }

    public static File download(String campaignId, String fileId)
        throws CampaignException {
        File retVal = null;
        
        S3Object entity =
            s3client.getObject(BUCKET_NAME, campaignId + "/" + fileId);
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
        s3client =
            AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.fromName(config.getRegion()))
                .build();
    }
}
