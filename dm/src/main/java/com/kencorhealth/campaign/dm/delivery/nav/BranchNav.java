package com.kencorhealth.campaign.dm.delivery.nav;

import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.util.List;
import java.util.Map;

public class BranchNav extends Nav implements Evaluatable {
    private IfCondition ifCondition;
    private List<ElseIfCondition> elseIfCondition;
    private ElseCondition elseCondition;

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
    public Nav findById(String id) {
        Nav retVal = super.findById(id);
        
        if (retVal == null) {
            if (ifCondition.getNav().getId().equals(id)) {
                retVal = ifCondition.getNav();
            } else if (CampaignUtil.valid(elseIfCondition)) {
                for (ElseIfCondition condition: elseIfCondition) {
                    if (condition.getNav().getId().equals(id)) {
                        retVal = condition.getNav();
                        break;
                    }
                }
            }
            
            if (retVal == null &&
                elseCondition != null &&
                elseCondition.getNav().getId().equals(id)) {
                retVal = elseCondition.getNav();
            }
        }
        
        return retVal;
    }

    @Override
    public Nav evaluate(Map<String, Object> data, Evaluator evaluator)
        throws CampaignException {
        Nav retVal = null;
        
        if (ifCondition.evaluate(data, evaluator)) {
            retVal = ifCondition.getNav();
        } else {
            if (CampaignUtil.valid(elseIfCondition)) {
                for (ElseIfCondition condition: elseIfCondition) {
                    if (condition.evaluate(data, evaluator)) {
                        retVal = condition.getNav();
                        break;
                    }
                }
            }
            
            if (retVal == null && elseCondition != null) {
                retVal = elseCondition.getNav();
            }
        }
        
        return retVal;
    }

    @Override
    public String toString() {
        return
            "BranchNav{" + "ifCondition=" + ifCondition +
            ", elseIfCondition=" + elseIfCondition + ", elseCondition=" +
            elseCondition + '}';
    }
}
