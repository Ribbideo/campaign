package com.kencorhealth.campaign.dm.common;

public class ItemSchedule {
    private String cronExpression;

    public ItemSchedule() {
        super();
    }

    public ItemSchedule(String cronExpression) {
        this.cronExpression = cronExpression;
    }
    
    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @Override
    public String toString() {
        return "ItemSchedule{" + "cronExpression=" + cronExpression + '}';
    }
}
