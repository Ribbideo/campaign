package com.kencorhealth.campaign.dm.delivery.type;

import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.Executor;
import com.kencorhealth.campaign.dm.delivery.DeliveryMethod;
import com.kencorhealth.campaign.dm.delivery.nav.Evaluatable;
import com.kencorhealth.campaign.dm.delivery.nav.Evaluator;
import com.kencorhealth.campaign.dm.delivery.nav.Executable;
import com.kencorhealth.campaign.dm.delivery.nav.Nav;
import java.util.List;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.util.Map;

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

    public Nav findNextById(
        String id,
        Map<String, Object> data,
        Executor executor,
        Evaluator evaluator) throws CampaignException {
        Nav retVal = null;
        
        if (CampaignUtil.valid(nav)) {
            Nav item = null;
            
            for (int i = 0; i < nav.size(); i++) {
                Nav atIndex = nav.get(i).findById(id);
                
                if (atIndex != null) {
                    item = i == nav.size()-1? atIndex : nav.get(i+1);
                    break;
                }
            }
            
            if (item instanceof Executable) {
                Executable executable = (Executable) item;
                retVal = executable.execute(data, executor);
            } else if (item instanceof Evaluatable) {
                Evaluatable evaluatable = (Evaluatable) item;
                retVal = evaluatable.evaluate(data, evaluator);
            } else {
                retVal = item;
            }
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
