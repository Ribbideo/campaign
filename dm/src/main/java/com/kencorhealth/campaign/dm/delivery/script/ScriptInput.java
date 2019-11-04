package com.kencorhealth.campaign.dm.delivery.script;

import com.kencorhealth.campaign.dm.delivery.nav.Nav;
import java.util.Map;

public class ScriptInput {
    private ScriptContext context;
    private Map<String, Object> formData;
    private Nav nav;

    public Nav getNav() {
        return nav;
    }

    public void setNav(Nav nav) {
        this.nav = nav;
    }
    
    public ScriptContext getContext() {
        return context;
    }
    
    public void setContext(ScriptContext context) {
        this.context = context;
    }

    public Map<String, Object> getFormData() {
        return formData;
    }

    public void setFormData(Map<String, Object> formData) {
        this.formData = formData;
    }

    @Override
    public String toString() {
        return
            "ScriptInput{" + "context=" + context + ", formData=" +
            formData + ", nav=" + nav + '}';
    }
}
