package com.kencorhealth.campaign.dm.delivery.nav;

import com.kencorhealth.campaign.dm.common.Transformer;
import com.kencorhealth.campaign.dm.exception.CampaignException;

public class MediaNav extends Nav {
    private String title;
    private String url;
    private String buttonText;
    private String hint;
    private MediaType mediaType;

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }
    
    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

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

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    public void transformFrom(Transformer transformer)
        throws CampaignException {
        this.title = transformer.transform(title);
        this.url = transformer.transform(url);
        this.buttonText = transformer.transform(buttonText);
        this.hint = transformer.transform(hint);
    }

    @Override
    public String toString() {
        return
            "MediaNav{" + "title=" + title + ", url=" + url +
            ", hint=" + hint + ", buttonText=" + buttonText +
            ", mediaType=" + mediaType + '}';
    }
}
