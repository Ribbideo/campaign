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
import com.kencorhealth.campaign.dm.exception.ExistsException;

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
            WorkflowData existing = values.get(0);
            existing.setInUse(true);

            Map<String, Map<String, Object>> data = existing.getData();

            Map<String, Map<String, Object>> newData = new HashMap();

            if (data != null) {
                newData.putAll(data);
            }

            newData.put(key, value);
            
            existing.setData(newData);

            update(existing);
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
            retVal = value.getData().get(key);
        } else {
            String message =
                "No data found for provider '" + providerId +
                "' and Id '" + containerId + "'";
            throw new NotFoundException(message);
        }
        
        return retVal;
    }    
}
