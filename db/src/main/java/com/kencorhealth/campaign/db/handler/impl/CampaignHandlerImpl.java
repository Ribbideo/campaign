package com.kencorhealth.campaign.db.handler.impl;

import com.kencorhealth.campaign.db.handler.CampaignHandler;
import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.ExistsException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.dm.input.CampaignInput;
import com.kencorhealth.campaign.json.JsonUtil;
import com.kencorhealth.campaign.mongo.handler.impl.MongoHandlerImpl;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.conversions.Bson;

public class CampaignHandlerImpl
    extends MongoHandlerImpl<Campaign, CampaignInput>
    implements CampaignHandler {
    public CampaignHandlerImpl(MongoClient mc) {
        super(mc);
    }
    
    @Override
    public Campaign findByClinicAndName(
        String clinicId, String name)
        throws NotFoundException, CampaignException {
        Campaign retVal = null;
        
        Map<String, Object> filter = new HashMap();
        filter.put(CLINIC_ID_KEY, clinicId);
        filter.put(NAME_KEY, name);
        
        List<Campaign> campaigns = findMany(filter);
        
        if (!campaigns.isEmpty()) {
            retVal = campaigns.get(0);
        } else {
            String message =
                "No campaign found for name '" + name +
                "' and clinic '" + clinicId + "'";
            
            throw new NotFoundException(message);
        }
        
        return retVal;
    }

    @Override
    public Campaign findByClinicAndId(
        String clinicId, String campaignId)
        throws NotFoundException, CampaignException {
        Campaign retVal = null;
        
        Map<String, Object> filter = new HashMap();
        filter.put(CLINIC_ID_KEY, clinicId);
        filter.put(ID_KEY, campaignId);
        
        List<Campaign> campaigns = findMany(filter);
        
        if (!campaigns.isEmpty()) {
            retVal = campaigns.get(0);
        } else {
            String message =
                "No campaign found for Id '" + campaignId +
                "' and clinic '" + clinicId + "'";
            
            throw new NotFoundException(message);
        }
        
        return retVal;
    }
    
    @Override
    public List<Campaign> findByClinic(String clinicId)
        throws CampaignException {
        Map<String, Object> filter = new HashMap();

        
        Bson orFilter =
            Filters.or(
                    Filters.eq(CLINIC_ID_KEY, clinicId), 
                    Filters.eq(CLINIC_ID_KEY, null)
                  );        
        
        Map<String, Object> or = new HashMap();
        List<Object> values = Arrays.asList(clinicId, null);
        or.put(CLINIC_ID_KEY, values);
        
        filter.put(OR_KEY, or);
        
        return doFindMany(orFilter);
    }
    
    @Override
    protected void doAdd(Campaign data)
        throws CampaignException, ExistsException {
        boolean proceed = false;
        
        String name = data.getName();
        String clinicId = data.getClinicId();
            
        Map<String, Object> filter = new HashMap();
        filter.put(NAME_KEY, name);
        filter.put(CLINIC_ID_KEY, clinicId);

        List<Campaign> campaigns = findMany(filter);

        if (!campaigns.isEmpty()) {
            String message =
                "Campaign with name '" + name + "' already exists";
            throw new ExistsException(message);
        } else {
            proceed = true;
        }
        
        if (proceed) {
            // We are good to go
            collection().insertOne(JsonUtil.asDoc(data));
        }
    }
}
