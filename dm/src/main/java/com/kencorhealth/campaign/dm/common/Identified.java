package com.kencorhealth.campaign.dm.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Identified {
    @JsonProperty("_id")
    private String id;
    private Long createTime;
    private Long updateTime;

    public void fillFrom(Identified another) {
        id = another.id;
        createTime = another.createTime;
        updateTime = another.updateTime;
    }
    
    public void updateUpdateTime() {
        ConvertSupplier cs = new ConvertSupplier() {};
        updateTime = cs.supplyCurrentTime();
    }
    
    public void autoFill() {
        ConvertSupplier cs = new ConvertSupplier() {};
        
        if (!CampaignUtil.valid(id)) {
            id = cs.supplyId();
        }
        
        Long currentTime = cs.supplyCurrentTime();
        
        createTime = currentTime;
        updateTime = currentTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
    
    @Override
    public String toString() {
        return
            "Identified{" + "id=" + id + ", createTime=" + createTime +
            ", updateTime=" + updateTime + '}';
    }
}
