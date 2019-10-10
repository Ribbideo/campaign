package com.kencorhealth.campaign.dm.delivery.web;

import java.util.Map;

public class FormData {
    private String id;
    private Map<String, Object> data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FormData{" + "id=" + id + ", data=" + data + '}';
    }
}
