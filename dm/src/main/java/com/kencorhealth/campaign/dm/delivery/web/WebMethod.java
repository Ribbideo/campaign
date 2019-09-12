package com.kencorhealth.campaign.dm.delivery.web;

import com.kencorhealth.campaign.dm.delivery.script.ScriptContext;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.Executor;
import com.kencorhealth.campaign.dm.delivery.DeliveryMethod;
import com.kencorhealth.campaign.dm.delivery.nav.Executable;
import com.kencorhealth.campaign.dm.delivery.nav.Nav;
import com.kencorhealth.campaign.dm.delivery.nav.QualifiedNav;
import com.kencorhealth.campaign.dm.delivery.script.ScriptInput;
import java.util.List;
import com.kencorhealth.campaign.dm.exception.CampaignException;

public class WebMethod implements DeliveryMethod {
    private List<Nav> nav;
    
    public Nav findById(String id) {
        Nav retVal = null;
        
        if (CampaignUtil.valid(nav)) {
            for (Nav item: nav) {
                retVal = item.findById(id);
                
                if (retVal != null) {
                    break;
                }
            }
        }
        
        return retVal;
    }

    public QualifiedNav findNextById(
        String navId,
        ScriptInput scriptInput,
        Executor executor) throws CampaignException {
        ScriptContext cd = scriptInput.getContext();
        
        QualifiedNav retVal = new QualifiedNav();
        retVal.setContainerId(cd.getContainerId());
        
        Nav scriptNav = scriptInput.getNav();
        
        if (scriptNav instanceof Executable) {
            Executable executable = (Executable) scriptNav;
            retVal.setNav(executable.execute(scriptInput, executor));
        } else if (CampaignUtil.valid(nav)) {
            Nav item = null;
            
            for (int i = 0; i < nav.size(); i++) {
                Nav atIndex = nav.get(i).findById(navId);
                
                if (atIndex != null) {
                    item = i == nav.size()-1? atIndex : nav.get(i+1);
                    break;
                }
            }
            
            retVal.setNav(item);
        }
        
        return retVal;
    }
    
    public List<Nav> getNav() {
        return nav;
    }

    public void setNav(List<Nav> nav) {
        this.nav = nav;
    }

    @Override
    public String toString() {
        return "WebMethod{" + "nav=" + nav + '}';
    }
}
