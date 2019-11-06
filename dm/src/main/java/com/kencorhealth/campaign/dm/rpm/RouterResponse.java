package com.kencorhealth.campaign.dm.rpm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "errors", "meta", "links", "included" })
public class RouterResponse {
    private RouterInfo data;

    public RouterInfo getData() {
        return data;
    }

    public void setData(RouterInfo data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RouterResponse{" + "data=" + data + '}';
    }
}
