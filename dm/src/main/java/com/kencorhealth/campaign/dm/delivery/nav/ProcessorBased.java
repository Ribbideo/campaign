package com.kencorhealth.campaign.dm.delivery.nav;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kencorhealth.campaign.dm.common.Script;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = PreProcessor.class, name = "pre-processor"),
    @JsonSubTypes.Type(value = Processor.class, name = "processor"),
    @JsonSubTypes.Type(value = PostProcessor.class, name = "post-processor")
})

public abstract class ProcessorBased {
    protected Script script;

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }
    
    @Override
    public String toString() {
        return "ProcessorBased{" + "script=" + script + '}';
    }
}
