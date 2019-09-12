package com.kencorhealth.campaign.dm.delivery.nav;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = IfCondition.class, name = "if"),
    @JsonSubTypes.Type(value = ElseIfCondition.class, name = "else-if"),
    @JsonSubTypes.Type(value = ElseCondition.class, name = "else")
})
public abstract class ConditionBased {
    private String condition;
    private Nav nav;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Nav getNav() {
        return nav;
    }

    public void setNav(Nav nav) {
        this.nav = nav;
    }

    @Override
    public String toString() {
        return "BranchItem{" + "condition=" + condition + ", nav=" + nav + '}';
    }
}
