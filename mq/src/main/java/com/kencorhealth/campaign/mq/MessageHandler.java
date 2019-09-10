package com.kencorhealth.campaign.mq;

import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.json.JsonUtil;

import java.lang.reflect.ParameterizedType;

public abstract class MessageHandler<T> {
    protected T data;

    public void setJSON(String json) throws CampaignException {
        data = JsonUtil.asObject(json, getDataType());
    }

    public abstract void process() throws Exception;

    public Class<T> getDataType() {
        return (Class<T>) ((ParameterizedType)
            getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
