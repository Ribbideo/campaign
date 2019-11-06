package com.kencorhealth.campaign.dm.delivery.nav;

import com.kencorhealth.campaign.dm.common.Transformable;
import com.kencorhealth.campaign.dm.common.Transformer;
import com.kencorhealth.campaign.dm.exception.CampaignException;

public class QualifiedNav implements Transformable {
    private String containerId;
    private Nav nav;

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public Nav getNav() {
        return nav;
    }

    public void setNav(Nav nav) {
        this.nav = nav;
    }
    
    @Override
    public void transformFrom(Transformer transformer)
        throws CampaignException {
        nav.transformFrom(transformer);
    }

    @Override
    public String toString() {
        return
            "QualifiedNav{" + "containerId=" + containerId +
            ", nav=" + nav + '}';
    }
}
