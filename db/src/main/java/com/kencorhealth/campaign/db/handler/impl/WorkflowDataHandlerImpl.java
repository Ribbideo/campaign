package com.kencorhealth.campaign.db.handler.impl;

import com.kencorhealth.campaign.dm.delivery.web.WorkflowData;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.DbException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.dm.input.WorkflowDataInput;
import com.kencorhealth.campaign.mongo.handler.impl.MongoHandlerImpl;
import com.mongodb.MongoClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kencorhealth.campaign.db.handler.WorkflowDataHandler;
import com.kencorhealth.campaign.dm.delivery.web.FormData;
import com.kencorhealth.campaign.dm.exception.ExistsException;
import java.util.ArrayList;

public class WorkflowDataHandlerImpl
    extends MongoHandlerImpl<WorkflowData, WorkflowDataInput>
    implements WorkflowDataHandler {
    public WorkflowDataHandlerImpl(MongoClient mc) {
        super(mc);
    }

    @Override
    public WorkflowData createOrGetUnused(String providerId, String campaignId)
        throws CampaignException, DbException {
        WorkflowData retVal = null;
        
        Map<String, Object> filter = new HashMap();
        filter.put(PROVIDER_ID_KEY, providerId);
        filter.put(CAMPAIGN_ID_KEY, campaignId);
        filter.put(IN_USE_KEY, false);
        
        List<WorkflowData> values = findMany(filter);
        
        if (!values.isEmpty()) {
            retVal = values.get(0);
        } else {
            WorkflowDataInput input = new WorkflowDataInput();
            input.setProviderId(providerId);
            input.setCampaignId(campaignId);
            try {
                retVal = input.convert();
                retVal.setInUse(true);
                doAdd(retVal);
            } catch (ExistsException e) {
                // Will not happen
                e.printStackTrace();
            }
        }
        
        return retVal;
    }

    @Override
    public void update(
        String providerId,
        String campaignId,
        String containerId,
        String key,
        Map<String, Object> value)
        throws NotFoundException, CampaignException, DbException {
        
        Map<String, Object> filter = new HashMap();
        filter.put(PROVIDER_ID_KEY, providerId);
        filter.put(CAMPAIGN_ID_KEY, campaignId);
        filter.put(ID_KEY, containerId);
        
        List<WorkflowData> values = findMany(filter);
        
        if (!values.isEmpty()) {
            WorkflowData wfd = values.get(0);
            wfd.setInUse(true);

            List<FormData> existingData = wfd.getFormData();

            List<FormData> newData = new ArrayList();

            if (existingData != null) {
                newData.addAll(existingData);
            }

            FormData fd = new FormData();
            fd.setId(key);
            fd.setData(value);
            
            newData.add(fd);
            
            wfd.setFormData(newData);

            update(wfd);
        } else {
            String message =
                "No data found for provider '" + providerId +
                "' and Id '" + containerId + "'";
            throw new NotFoundException(message);
        }
    }
    
    @Override
    public Map<String, Object> get(
        String providerId,
        String campaignId,
        String containerId,
        String key)
        throws NotFoundException, CampaignException, DbException {
        Map<String, Object> retVal = null;
        
        Map<String, Object> filter = new HashMap();
        filter.put(PROVIDER_ID_KEY, providerId);
        filter.put(CAMPAIGN_ID_KEY, campaignId);
        filter.put(ID_KEY, containerId);
        
        List<WorkflowData> values = findMany(filter);
        
        if (!values.isEmpty()) {
            WorkflowData value = values.get(0);
            
            List<FormData> formData = value.getFormData();
            
            for (FormData fd: formData) {
                if (fd.getId().equals(key)) {
                    retVal = fd.getData();
                    break;
                }
            }
        } else {
            String message =
                "No data found for provider '" + providerId +
                "' and Id '" + containerId + "'";
            throw new NotFoundException(message);
        }
        
        return retVal;
    }    

    @Override
    public Map<String, Object> get(
        String providerId,
        String campaignId,
        String containerId,
        int index)
        throws NotFoundException, CampaignException, DbException {
        Map<String, Object> retVal = null;
        
        Map<String, Object> filter = new HashMap();
        filter.put(PROVIDER_ID_KEY, providerId);
        filter.put(CAMPAIGN_ID_KEY, campaignId);
        filter.put(ID_KEY, containerId);
        
        List<WorkflowData> values = findMany(filter);
        
        if (!values.isEmpty()) {
            WorkflowData value = values.get(0);
            
            retVal = value.getFormData().get(0).getData();
        } else {
            String message =
                "No data found for provider '" + providerId +
                "' and Id '" + containerId + "'";
            throw new NotFoundException(message);
        }
        
        return retVal;
    }    
}
