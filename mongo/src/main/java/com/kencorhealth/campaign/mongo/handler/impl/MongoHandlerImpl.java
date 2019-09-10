package com.kencorhealth.campaign.mongo.handler.impl;

import com.kencorhealth.campaign.dm.common.Identified;
import com.kencorhealth.campaign.dm.common.Input;
import com.kencorhealth.campaign.dm.common.Named;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.DbException;
import com.kencorhealth.campaign.dm.exception.ExistsException;
import com.kencorhealth.campaign.dm.exception.InvalidException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.json.JsonUtil;
import com.kencorhealth.campaign.mongo.handler.MongoHandler;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bson.Document;

public abstract class MongoHandlerImpl<T extends Identified, TI extends Input>
    implements MongoHandler<T, TI> {
    protected final MongoClient mc;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    
    private final transient Class<T> type;
    
    public MongoHandlerImpl(MongoClient mc) {
        this.mc = mc;
        
        ParameterizedType pt =
            (ParameterizedType) getClass().getGenericSuperclass();
        
        this.type = (Class<T>)pt.getActualTypeArguments()[0];
    }
    
    @Override
    public String add(TI input)
        throws InvalidException, NotFoundException, ExistsException,
        DbException,  CampaignException {
        return doAdd(input);
    }

    @Override
    public void update(T data)
        throws NotFoundException, DbException, CampaignException {
        doUpdate(data);
    }

    @Override
    public T findById(String id)
        throws NotFoundException, CampaignException {
        return doFindBy(ID_KEY, id);
    }
    
    @Override
    public T findByName(String name)
        throws NotFoundException, CampaignException {
        return doFindBy(NAME_KEY, name);
    }

    @Override
    public List<T> findMany(Map<String, Object> filter)
        throws CampaignException {
        List<Document> filterDoc = new ArrayList();
        
        filter.forEach((key, value) -> filterDoc.add(new Document(key, value)));
        
        Document and = new Document("$and", filterDoc);
        
        FindIterable<Document> result = collection().find(and);
        
        return JsonUtil.asList(result, type);
    }
    
    @Override
    public List<T> findAll() throws CampaignException {
        FindIterable<Document> result = collection().find();
        
        return JsonUtil.asList(result, type);
    }
    
    private T doFindBy(String key, String value)
        throws NotFoundException, CampaignException {
        Document doc =
            collection().find(
                eq(key, value)
            ).first();
        
        T retVal = JsonUtil.asObject(doc, type);
        
        if (retVal == null) {
            String message = "No data found for " + key + "=" + value;
            throw new NotFoundException(message);
        }
        
        return retVal;
    } 

    @Override
    public void ping() throws DbException {
        Document ping = new Document("ping", "1");
        
        try {
            database().runCommand(ping);
        } catch (MongoException e) {
             throw new DbException("MongoDB ping error", e);
        }        
    }
    
    protected MongoCollection<Document> collection() {
        if (collection == null) {
            collection = database().getCollection(collectionName());
        }
        
        return collection;
    }

    protected MongoDatabase database() {
        if (database == null) {
            database = mc.getDatabase(databaseName());
        }
        
        return database;
    }

    protected <TI extends Input> String doAdd(TI input)
        throws ExistsException, CampaignException {
        String retVal = null;
        
        Identified identified = input.convert();
        
        boolean proceed = false;
        
        if (identified instanceof Named) {
            Named named = (Named) identified;
            
            String name = named.getName();
            
            try {
                findByName(name);
                String message =
                    "Record with name '" + name + "' already exists";
                throw new ExistsException(message);
            } catch (NotFoundException e) {
                proceed = true;
            }
        } else {
            // No need to check for duplicate
            proceed = true;
        }
        
        if (proceed) {
            // We are good to go
            collection().insertOne(JsonUtil.asDoc(identified));
            retVal = identified.getId();
        }
        
        return retVal;
    }

    protected <T extends Identified> void doUpdate(T identified)
        throws NotFoundException, CampaignException {
        findById(identified.getId());
        
        identified.updateUpdateTime();

        collection().findOneAndReplace(
            eq(ID_KEY, identified.getId()),
            JsonUtil.asDoc(identified)
        );
    }
}