package com.kencorhealth.campaign.http.base.handler.impl;

import com.kencorhealth.campaign.dm.common.RequestType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.http.base.handler.HttpBasedHandler;
import com.kencorhealth.campaign.json.JsonUtil;
import java.nio.charset.Charset;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public abstract class HttpBasedHandlerImpl implements HttpBasedHandler {
    private String baseUrl;
    private String authorization;
    protected String contentType;
    private boolean internalMode;

    @Override
    public void setBaseUrl(String baseUrl, boolean internalMode) {
        this.baseUrl = baseUrl;
        this.internalMode = internalMode;
    }

    @Override
    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
    
    public  <T> T sendGet(
        Map<String, String> queryParams,
        JavaType responseType,
        String... endpoints)
        throws CampaignException {
        return
            doSendRequest(
                RequestType.GET,
                queryParams,
                null,
                responseType,
                endpoints
            );
    }

    public  <T> T sendGet(
        Map<String, String> queryParams,
        Type responseType,
        String... endpoints)
        throws CampaignException {
        return
            doSendRequest(
                RequestType.GET,
                queryParams,
                null,
                responseType,
                endpoints
            );
    }

    public  <T> T sendGet(
        Map<String, String> queryParams,
        TypeReference responseType,
        String... endpoints)
        throws CampaignException {
        return
            doSendRequest(
                RequestType.GET,
                queryParams,
                null,
                responseType,
                endpoints
            );
    }

    public  <T> T sendPost(
        Map<String, String> queryParams,
        Object body,
        JavaType responseType,
        String... endpoints) throws CampaignException {
        return
            doSendRequest(
                RequestType.POST,
                queryParams,
                body,
                responseType,
                endpoints
            );
    }
    
    public  <T> T sendPost(
        Map<String, String> queryParams,
        Object body,
        Type responseType,
        String... endpoints) throws CampaignException {
        return
            doSendRequest(
                RequestType.POST,
                queryParams,
                body,
                responseType,
                endpoints
            );
    }
    
    public  <T> T sendPost(
        Map<String, String> queryParams,
        Object body,
        TypeReference responseType,
        String... endpoints) throws CampaignException {
        return
            doSendRequest(
                RequestType.POST,
                queryParams,
                body,
                responseType,
                endpoints
            );
    }
    
    public  <T> T sendPut(
        Map<String, String> queryParams,
        Object body,
        JavaType responseType,
        String... endpoints) throws CampaignException {
        return
            doSendRequest(
                RequestType.PUT,
                queryParams,
                body,
                responseType,
                endpoints
            );
    }
    
    public  <T> T sendPut(
        Map<String, String> queryParams,
        Object body,
        Type responseType,
        String... endpoints) throws CampaignException {
        return
            doSendRequest(
                RequestType.PUT,
                queryParams,
                body,
                responseType,
                endpoints
            );
    }
    
    public  <T> T sendPut(
        Map<String, String> queryParams,
        Object body,
        TypeReference responseType,
        String... endpoints) throws CampaignException {
        return
            doSendRequest(
                RequestType.PUT,
                queryParams,
                body,
                responseType,
                endpoints
            );
    }
    
    public  <T> T sendDelete(
        Map<String, String> queryParams,
        JavaType responseType,
        String... endpoints) throws CampaignException {
        return
            doSendRequest(
                RequestType.DELETE,
                queryParams,
                null,
                responseType,
                endpoints
            );
    }
    
    public  <T> T sendDelete(
        Map<String, String> queryParams,
        Type responseType,
        String... endpoints) throws CampaignException {
        return
            doSendRequest(
                RequestType.DELETE,
                queryParams,
                null,
                responseType,
                endpoints
            );
    }
    
    public  <T> T sendDelete(
        Map<String, String> queryParams,
        TypeReference responseType,
        String... endpoints) throws CampaignException {
        return
            doSendRequest(
                RequestType.DELETE,
                queryParams,
                null,
                responseType,
                endpoints
            );
    }

    private  <T> T doSendRequest(
        RequestType requestType,
        Map<String, String> queryParams,
        Object body,
        Object responseType,
        String... endpoints) throws CampaignException {
        T retVal = null;
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        HttpRequestBase request = null;
        
        String fullUri = makeUri(queryParams, endpoints);
        
        switch (requestType) {
            case DELETE:
                request = new HttpDelete(fullUri);
                break;
            case GET:
                request = new HttpGet(fullUri);
                break;
            case POST:
                request = new HttpPost(fullUri);
                break;
            case PUT:
                request = new HttpPut(fullUri);
                break;
        }
        
        try {
            if (CampaignUtil.valid(contentType)) {
                request.setHeader("Content-Type", contentType);
                
                if (request instanceof HttpEntityEnclosingRequestBase) {
                    HttpEntityEnclosingRequestBase payloadBased =
                        (HttpEntityEnclosingRequestBase) request;
                    
                    List<NameValuePair> form = new ArrayList();

                    queryParams.forEach((key, value) -> {
                        form.add(new BasicNameValuePair(key, value));
                    });

                    UrlEncodedFormEntity entity =
                        new UrlEncodedFormEntity(form, Consts.UTF_8);                
                    payloadBased.setEntity(entity);
                }
            } else {
                request.setHeader("Content-Type", "application/json");
            }
            
            if (CampaignUtil.valid(authorization)) {
                request.setHeader("Authorization", authorization);
            }
            
            if (body != null) {
                HttpEntityEnclosingRequestBase entityBased =
                    (HttpEntityEnclosingRequestBase) request;
                
                String json = JsonUtil.asJson(body);
                
                entityBased.setEntity(
                    new StringEntity(json, Charset.forName("UTF-8"))
                );
            }

            HttpResponse response = httpClient.execute(request);
            
            int statusCode = response.getStatusLine().getStatusCode();
            
            int expectedCode = request instanceof HttpPost ? 201 : 200;
            
            if (statusCode == expectedCode ||
                (request instanceof HttpPost && statusCode == 200)) {
                String json =
                    EntityUtils.toString(response.getEntity(), "UTF-8");
                
                if (responseType instanceof JavaType) {
                    JavaType jt = (JavaType) responseType;
                    retVal = JsonUtil.asObject(json, jt);
                } else if (responseType instanceof TypeReference) {
                    TypeReference tr = (TypeReference) responseType;
                    retVal = JsonUtil.asObject(json, tr);
                } else {
                    Type t = (Type) responseType;
                    retVal = JsonUtil.asObject(json, t);
                }
            } else if (statusCode != 404 && statusCode != 204) {
                String message =
                    "Expected response code " + expectedCode +
                    ", got " + statusCode;
                
                throw new CampaignException(message);
            }
        } catch (Exception e) {
            throw new CampaignException(e);
        }
        
        return retVal;
    }

    private String makeUri(
        Map<String, String> queryParams, String... endpoints) {
        String retVal = null;
        
        StringBuilder uri = new StringBuilder(baseUrl);
        
        if (internalMode) {
            uri.append("api");
        }
        
        for (String endpoint: endpoints) {
            uri.append("/").append(CampaignUtil.urlSafe(endpoint));
        }
        
        try {
            URIBuilder builder = new URIBuilder(uri.toString());
            
            if (queryParams != null) {
                queryParams.forEach((key, value) -> {
                    if (!CampaignUtil.valid(contentType)) {
                        builder.setParameter(key, CampaignUtil.urlSafe(value));
                    }
                });
            }
            
            retVal = builder.build().toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return retVal;
    }
}
