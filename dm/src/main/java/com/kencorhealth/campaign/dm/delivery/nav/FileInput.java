package com.kencorhealth.campaign.dm.delivery.nav;

import com.kencorhealth.campaign.dm.common.Transformer;
import com.kencorhealth.campaign.dm.exception.CampaignException;

public class FileInput extends InputBased {
    private String hint;

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    public void transformFrom(Transformer transformer)
        throws CampaignException {
        this.hint = transformer.transform(hint);
    }
    
    @Override
    public String toString() {
        return "FileInput{" + "hint=" + hint + "}, " + super.toString();
    }
}
