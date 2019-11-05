package com.kencorhealth.campaign.db.handler.impl;

import com.kencorhealth.campaign.db.handler.CampaignHandler;
import com.kencorhealth.campaign.db.handler.ProviderHandler;
import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.ExistsException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.dm.input.CampaignInput;
import com.kencorhealth.campaign.json.JsonUtil;
import com.kencorhealth.campaign.mongo.handler.impl.MongoHandlerImpl;
import com.mongodb.MongoClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampaignHandlerImpl
    extends MongoHandlerImpl<Campaign, CampaignInput>
    implements CampaignHandler {
    public CampaignHandlerImpl(MongoClient mc) {
        super(mc);
    }
    
    @Override
    public Campaign findByProviderAndName(
        String providerId, String name)
        throws NotFoundException, CampaignException {
        Campaign retVal = null;
        
        try (ProviderHandler ph = new ProviderHandlerImpl(mc)) {
            ph.findById(providerId);
        } catch (Exception e) {
            throw new CampaignException(e);
        }
        
        Map<String, Object> filter = new HashMap();
        filter.put(PROVIDER_ID_KEY, providerId);
        filter.put(NAME_KEY, name);
        
        List<Campaign> campaigns = findMany(filter);
        
        if (!campaigns.isEmpty()) {
            retVal = campaigns.get(0);
        } else {
            String message =
                "No campaign found for name '" + name +
                "' and provider '" + providerId + "'";
            
            throw new NotFoundException(message);
        }
        
        return retVal;
    }

    @Override
    public Campaign findByProviderAndId(
        String providerId, String campaignId)
        throws NotFoundException, CampaignException {
        Campaign retVal = null;
        
        try (ProviderHandler ph = new ProviderHandlerImpl(mc)) {
            ph.findById(providerId);
        } catch (Exception e) {
            throw new CampaignException(e);
        }
        
        Map<String, Object> filter = new HashMap();
        filter.put(PROVIDER_ID_KEY, providerId);
        filter.put(ID_KEY, campaignId);
        
        List<Campaign> campaigns = findMany(filter);
        
        if (!campaigns.isEmpty()) {
            retVal = campaigns.get(0);
        } else {
            String message =
                "No campaign found for Id '" + campaignId +
                "' and provider '" + providerId + "'";
            
            throw new NotFoundException(message);
        }
        
        return retVal;
    }
    
    @Override
    public List<Campaign> findByProvider(String providerId)
        throws CampaignException {
        Map<String, Object> filter = new HashMap();
        filter.put(PROVIDER_ID_KEY, providerId);
        
        return findMany(filter);
    }
    
    @Override
    protected void doAdd(Campaign data)
        throws CampaignException, ExistsException {
        boolean proceed = false;
        
        String name = data.getName();
        String providerId = data.getProviderId();
            
        Map<String, Object> filter = new HashMap();
        filter.put(NAME_KEY, name);
        filter.put(PROVIDER_ID_KEY, providerId);

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
