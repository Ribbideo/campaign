package com.kencorhealth.campaign.dm.delivery.nav;

import com.kencorhealth.campaign.dm.common.Transformer;
import com.kencorhealth.campaign.dm.exception.CampaignException;

public class TitleNav extends Nav {
    private String title;
    private String buttonText;
    private String hint;
    private String logoUrl;

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
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
        this.buttonText = transformer.transform(buttonText);
        this.hint = transformer.transform(hint);
        this.logoUrl = transformer.transform(logoUrl);
    }

    @Override
    public String toString() {
        return
            "TitleNav{" + "title=" + title + ", buttonText=" + buttonText +
            ", hint=" + hint + ", logoUrl=" + logoUrl + '}';
    }
}
