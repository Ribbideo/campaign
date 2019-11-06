package com.kencorhealth.campaign.dm.delivery.nav;

import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.Transformer;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.util.List;

public class FormNav extends ProcessingBasedNav implements Executable {
    private List<DisplayBased> items;
    private String title;
    private String buttonText;
    private String hint;

    public FormNav() {
        super();
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
    
    public List<DisplayBased> getItems() {
        return items;
    }

    public void setItems(List<DisplayBased> items) {
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void transformFrom(Transformer transformer)
        throws CampaignException {
        this.title = transformer.transform(title);
        this.buttonText = transformer.transform(buttonText);
        this.hint = transformer.transform(hint);
        
        if (CampaignUtil.valid(items)) {
            for (DisplayBased item: items) {
                item.transformFrom(transformer);
            }
        }
    }

    @Override
    public String toString() {
        return
            "FormNav{" + "items=" + items + ", title=" + title +
            ", buttonText=" + buttonText + ", hint=" + hint + "}, " +
            super.toString();
    }
}
