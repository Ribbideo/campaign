package com.kencorhealth.campaign.dm.delivery.nav;

import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.Transformer;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.util.List;

public class BranchNav extends Nav {
    private IfCondition ifCondition;
    private List<ElseIfCondition> elseIfCondition;
    private ElseCondition elseCondition;
    private Nav failureNav;

    public Nav getFailureNav() {
        return failureNav;
    }

    public void setFailureNav(Nav failureNav) {
        this.failureNav = failureNav;
    }
    
    public IfCondition getIfCondition() {
        return ifCondition;
    }

    public void setIfCondition(IfCondition ifCondition) {
        this.ifCondition = ifCondition;
    }

    public List<ElseIfCondition> getElseIfCondition() {
        return elseIfCondition;
    }

    public void setElseIfCondition(List<ElseIfCondition> elseIfCondition) {
        this.elseIfCondition = elseIfCondition;
    }

    public ElseCondition getElseCondition() {
        return elseCondition;
    }

    public void setElseCondition(ElseCondition elseCondition) {
        this.elseCondition = elseCondition;
    }

    @Override
    public void transformFrom(Transformer transformer)
        throws CampaignException {
        ifCondition.transformFrom(transformer);
        
        if (elseIfCondition != null) {
            for (ElseIfCondition item: elseIfCondition) {
                item.transformFrom(transformer);
            }
        }
        
        if (elseCondition != null) {
            elseCondition.transformFrom(transformer);
        }
        
        if (failureNav != null) {
            failureNav.transformFrom(transformer);
        }
    }

    @Override
    public Nav findById(String id) {
        Nav retVal = super.findById(id);
        
        if (retVal == null) {
            retVal = ifCondition.getNav().findById(id);
        }
            
        if (retVal == null && CampaignUtil.valid(elseIfCondition)) {
            for (ElseIfCondition eic: elseIfCondition) {
                retVal = eic.getNav().findById(id);

                if (retVal != null) {
                    break;
                }
            }
        }

        if (retVal == null &&
            elseCondition != null &&
            elseCondition.getNav().getId().equals(id)) {
            retVal = elseCondition.getNav().findById(id);
        }

        if (retVal == null && failureNav != null) {
            retVal = failureNav.findById(id);
        }
        
        return retVal;
    }

    @Override
    public String toString() {
        return
            "BranchNav{" + "ifCondition=" + ifCondition +
            ", elseIfCondition=" + elseIfCondition + ", elseCondition=" +
            elseCondition + ", failureNav=" + failureNav + '}';
    }
}
