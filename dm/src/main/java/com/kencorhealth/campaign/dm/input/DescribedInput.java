package com.kencorhealth.campaign.dm.input;

import com.kencorhealth.campaign.dm.common.Described;

public abstract class DescribedInput<T extends Described>
    extends NamedInput<T> {
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    @Override
    public void doConvert(T data) {
        super.doConvert(data);
        data.setDescription(description);
    }

    @Override
    public String toString() {
        return
            "DescribedInput{" + "description=" + description + "}, " +
            super.toString();
    }
}
