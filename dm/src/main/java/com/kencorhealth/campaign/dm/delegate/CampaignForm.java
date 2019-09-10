package com.kencorhealth.campaign.dm.delegate;

import com.kencorhealth.campaign.dm.delivery.nav.ProcessorBased;
import java.util.Map;

public class CampaignForm extends CampaignBasedData {
    private String formId;
    private ProcessorBased processor;
    private Map<String, Object> data;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
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
            "CampaignForm{" + "data=" + data + ", processor=" + processor +
            ", formId=" + formId + "}, " + super.toString();
    }
}
