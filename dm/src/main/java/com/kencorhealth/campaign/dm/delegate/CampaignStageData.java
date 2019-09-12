package com.kencorhealth.campaign.dm.delegate;

import com.kencorhealth.campaign.dm.delivery.nav.ProcessorBased;
import com.kencorhealth.campaign.dm.delivery.script.ScriptInput;

public class CampaignStageData {
    private ProcessorBased processor;
    private ScriptInput scriptInput;

    public ScriptInput getScriptInput() {
        return scriptInput;
    }

    public void setScriptInput(ScriptInput scriptInput) {
        this.scriptInput = scriptInput;
    }

    public ProcessorBased getProcessor() {
        return processor;
    }

    public void setProcessor(ProcessorBased processor) {
        this.processor = processor;
    }

    @Override
    public String toString() {
        return
            "CampaignStageData{" + "scriptInput=" + scriptInput +
            ", processor=" + processor + "}, " + super.toString();
    }
}
