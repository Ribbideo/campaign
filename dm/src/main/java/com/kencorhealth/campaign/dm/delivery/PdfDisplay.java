package com.kencorhealth.campaign.dm.delivery;

import com.kencorhealth.campaign.dm.common.Transformer;
import com.kencorhealth.campaign.dm.delivery.nav.*;
import com.kencorhealth.campaign.dm.exception.CampaignException;

public class PdfDisplay extends DisplayBased {
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
    public void transformFrom(Transformer transformer)
        throws CampaignException {
        this.title = transformer.transform(title);
        this.url = transformer.transform(url);
    }

    @Override
    public String toString() {
        return "PdfDisplay{" + "title=" + title + ", url=" + url + "}";
    }
}
