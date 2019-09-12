package com.kencorhealth.campaign.dm.delivery.nav;

public class QualifiedNav {
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
    public String toString() {
        return
            "QualifiedNav{" + "containerId=" + containerId +
            ", nav=" + nav + '}';
    }
}
