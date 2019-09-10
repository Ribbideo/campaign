package com.kencorhealth.campaign.dm.common;

public abstract class Named extends Identified {
    private String name;

    @Override
    public void fillFrom(Identified another) {
        super.fillFrom(another);
        
        Named named = (Named) another;
        
        name = named.name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Named{" + "name=" + name + "}, " + super.toString();
    }
}
