package com.kencorhealth.campaign.dm.delivery.nav;

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
    public String toString() {
        return
            "TitleNav{" + "title=" + title + ", buttonText=" + buttonText +
            ", hint=" + hint + ", logoUrl=" + logoUrl + '}';
    }
}
