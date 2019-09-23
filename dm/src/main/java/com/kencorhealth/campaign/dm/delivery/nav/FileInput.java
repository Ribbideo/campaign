package com.kencorhealth.campaign.dm.delivery.nav;

public class FileInput extends InputBased {
    private String hint;

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    public String toString() {
        return "FileInput{" + "hint=" + hint + "}, " + super.toString();
    }
}
