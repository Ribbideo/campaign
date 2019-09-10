package com.kencorhealth.campaign.db.handler.impl;

import com.kencorhealth.campaign.db.handler.ProviderHandler;
import com.kencorhealth.campaign.dm.provider.Provider;
import com.kencorhealth.campaign.dm.provider.ProviderType;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.dm.input.ProviderInput;
import com.kencorhealth.campaign.mongo.handler.impl.MongoHandlerImpl;
import com.mongodb.MongoClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProviderHandlerImpl
    extends MongoHandlerImpl<Provider, ProviderInput>
    implements ProviderHandler {
    public ProviderHandlerImpl(MongoClient mc) {
        super(mc);
    }
    
    @Override
    public Provider findByNameAndType(String name, ProviderType type)
        throws NotFoundException, CampaignException {
        Map<String, Object> filter = new HashMap();
        filter.put(NAME_KEY, name);
        filter.put(PROVIDER_TYPE_KEY, type.name());
        
        List<Provider> providers = findMany(filter);
        
        if (providers.isEmpty()) {
            throw new NotFoundException("Provider not found");
        }
        
        return providers.get(0);
    }
}
