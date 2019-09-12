package com.kencorhealth.campaign.dm.delivery.nav;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kencorhealth.campaign.dm.common.Executor;
import com.kencorhealth.campaign.dm.common.RequestType;
import com.kencorhealth.campaign.dm.delegate.CampaignStageData;
import com.kencorhealth.campaign.dm.delivery.script.ScriptInput;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ChoiceNav.class, name = "choice"),
    @JsonSubTypes.Type(value = FormNav.class, name = "form")
})
public abstract class ProcessingBasedNav extends Nav implements Executable {
    private Processing processing;
    private Nav successNav;
    private Nav failureNav;

    public ProcessingBasedNav() {
        super();
        requestType = RequestType.POST;
    }
    
    @Override
    public Nav execute(
        ScriptInput scriptInput,
        Executor executor) throws CampaignException {
        Nav retVal = null;
        
        PreProcessor preProcessor = processing.getPreProcessor();
        Processor processor = processing.getProcessor();
        
        List<Object> args = new ArrayList();

        boolean proceed = true;
        
        if (preProcessor != null) {
            args.add(preProcessor);
            proceed = (boolean) executor.execute(scriptInput, args.toArray());
        }
        
        if (proceed) {
            if (processor != null) {
                args.clear();
                args.add(processor);
            }

            retVal = (Nav) executor.execute(scriptInput, args.toArray());
            
            PostProcessor postProcessor = processing.getPostProcessor();
            
            if (!retVal.mustAbort()) {
                CampaignStageData csd = new CampaignStageData();
                csd.setScriptInput(scriptInput);
                csd.setProcessor(postProcessor);
                
                executor.getDispatcher().dispatchCampaignStageData(csd);
            }
        } else {
            throw new CampaignException("Pre-processor failed");
        }

        return retVal;
    }
    
    @Override
    public Nav findById(String id) {
        Nav retVal = super.findById(id);
        
        if (retVal == null) {
            if (successNav != null) {
                retVal = successNav.findById(id);
            }
            
            if (retVal == null && failureNav != null) {
                retVal = failureNav.findById(id);
            }
        }
        
        return retVal;
    }
    
    @Override
    public void sanitize() {
        super.sanitize();
        processing = null;
        successNav = null;
        failureNav = null;
    }

    public Nav getFailureNav() {
        return failureNav;
    }

    public void setFailureNav(Nav failureNav) {
        this.failureNav = failureNav;
    }

    public Nav getSuccessNav() {
        return successNav;
    }

    public void setSuccessNav(Nav successNav) {
        this.successNav = successNav;
    }
    
    public Processing getProcessing() {
        return processing;
    }

    public void setProcessing(Processing processing) {
        this.processing = processing;
    }

    @Override
    public String toString() {
        return
            "ProcessingBasedNav{" + "processing=" + processing +
            ", successNav=" + successNav + ", failureNav=" + failureNav +
            "}, " + super.toString();
    }
}
