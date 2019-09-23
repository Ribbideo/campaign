package com.kencorhealth.campaign.dm.delivery;

import com.kencorhealth.campaign.dm.delivery.nav.*;

public class UrlDisplay extends DisplayBased {
    private String title;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UrlDisplay{" + "title=" + title + ", url=" + url + "}";
    }
}
