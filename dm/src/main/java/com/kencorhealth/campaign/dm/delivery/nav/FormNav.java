package com.kencorhealth.campaign.dm.delivery.nav;

import com.kencorhealth.campaign.dm.common.Executor;
import com.kencorhealth.campaign.dm.common.RequestType;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FormNav extends Nav implements Executable {
    private List<InputBased> items;
    private String title;
    private String buttonText;
    private String hint;
    private Processing processing;
    private Nav submitSuccess;
    private Nav submitFailure;

    public FormNav() {
        super();
        setRequestType(RequestType.POST);
    }

    @Override
    public Nav execute(Map<String, Object> data, Executor executor)
        throws CampaignException {
        Nav retVal = null;
        
        PreProcessor preProcessor = processing.getPreProcessor();
        Processor processor = processing.getProcessor();
        
        List<Object> args = new ArrayList();

        if (preProcessor != null) {
            args.add(preProcessor);
        }
        
        if (processor != null) {
            args.add(processor);
        }
        
        try {
            executor.execute(data, args.toArray());
            retVal = submitSuccess;
        } catch (CampaignException e) {
            e.printStackTrace();
            retVal = submitFailure;
        }

        return retVal;
    }
    
    @Override
    public Nav findById(String id) {
        Nav retVal = super.findById(id);
        
        if (retVal == null) {
            if (submitSuccess != null && submitSuccess.getId().equals(id)) {
                retVal = submitSuccess;
            } else if (submitFailure != null && submitFailure.getId().equals(id)) {
                retVal = submitFailure;
            }
        }
        
        return retVal;
    }
    
    @Override
    public void sanitize() {
        super.sanitize();
        processing = null;
        submitSuccess = null;
        submitFailure = null;
    }

    public Processing getProcessing() {
        return processing;
    }

    public void setProcessing(Processing processing) {
        this.processing = processing;
    }

    public Nav getSubmitSuccess() {
        return submitSuccess;
    }

    public void setSubmitSuccess(Nav submitSuccess) {
        this.submitSuccess = submitSuccess;
    }

    public Nav getSubmitFailure() {
        return submitFailure;
    }

    public void setSubmitFailure(Nav submitFailure) {
        this.submitFailure = submitFailure;
    }
    
    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
    
    public List<InputBased> getItems() {
        return items;
    }

    public void setItems(List<InputBased> items) {
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return
            "FormNav{" + "items=" + items + ", title=" + title +
            ", buttonText=" + buttonText + ", hint=" + hint + "}, " +
            ", submitSuccess=" + submitSuccess +
            ", submitFailure=" + submitFailure +
            ", processing=" + processing +
            super.toString();
    }
}
