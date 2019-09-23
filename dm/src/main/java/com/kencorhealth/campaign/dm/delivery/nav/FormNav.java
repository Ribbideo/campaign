package com.kencorhealth.campaign.dm.delivery.nav;

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
    public String toString() {
        return
            "FormNav{" + "items=" + items + ", title=" + title +
            ", buttonText=" + buttonText + ", hint=" + hint + "}, " +
            super.toString();
    }
}
