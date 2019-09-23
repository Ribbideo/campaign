package com.kencorhealth.campaign.dm.delivery.nav;

public class TextInput extends InputBased {
    private String title;
    private String hint;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
            "TextInput{" + "title=" + title + ", hint=" + hint + "}, " +
            super.toString();
    }
}
