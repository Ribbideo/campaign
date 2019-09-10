package com.kencorhealth.campaign.mongo.handler;

import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.Identified;
import com.kencorhealth.campaign.dm.common.Input;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.DbException;
import com.kencorhealth.campaign.dm.exception.ExistsException;
import com.kencorhealth.campaign.dm.exception.InvalidException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.mongo.MongoConstants;
import java.util.List;
import java.util.Map;

public interface MongoHandler<T extends Identified, TI extends Input>
    extends AutoCloseable, MongoConstants {
    String collectionName();
    default String alias() {
        return CampaignUtil.smartLowerCase(collectionName());
    }
    String databaseName();
    
    String add(TI input)
        throws InvalidException, NotFoundException, ExistsException,
        DbException, CampaignException;
    void update(T data)
        throws NotFoundException, DbException, CampaignException;
    T findById(String id) throws NotFoundException, CampaignException;
    T findByName(String name) throws NotFoundException, CampaignException;
    List<T> findMany(Map<String, Object> filter) throws CampaignException;
    List<T> findAll() throws CampaignException;
    
    void ping() throws DbException;
    @Override
    default void close() {
        // TODO?
    }
}
