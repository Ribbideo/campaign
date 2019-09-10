package com.kencorhealth.campaign.dm.common;

public abstract class Described extends Named {
    private String description;
    
    @Override
    public void fillFrom(Identified another) {
        super.fillFrom(another);
        
        Described described = (Described) another;
        
        description = described.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return
            "Described{" + "description=" + description + "}, " +
            super.toString();
    }
}
