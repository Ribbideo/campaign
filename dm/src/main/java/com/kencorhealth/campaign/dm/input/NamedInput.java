package com.kencorhealth.campaign.dm.input;

import com.kencorhealth.campaign.dm.common.Input;
import com.kencorhealth.campaign.dm.common.Named;

public abstract class NamedInput<T extends Named> extends Input<T> {
    private String name;

    public void doConvert(T data) {
        data.autoFill();
        data.setName(name);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NamedInput{" + "name=" + name;
    }
}
