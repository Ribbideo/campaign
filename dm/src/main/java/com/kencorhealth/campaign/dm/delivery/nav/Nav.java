package com.kencorhealth.campaign.dm.delivery.nav;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.dm.common.RequestType;
import com.kencorhealth.campaign.dm.common.Sanitizer;
import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = TitleNav.class, name = "title"),
    @JsonSubTypes.Type(value = MediaNav.class, name = "media"),
    @JsonSubTypes.Type(value = BranchNav.class, name = "branch"),
    @JsonSubTypes.Type(value = ChoiceNav.class, name = "choice"),
    @JsonSubTypes.Type(value = FormNav.class, name = "form")
})
public abstract class Nav implements Sanitizer {
    private String id;
    private RequestType requestType;
    
    public Nav() {
        id = CampaignUtil.uniqueString();
        requestType = RequestType.PUT;
    }
    
    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Nav other = (Nav) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    public Nav findById(String id) {
        // Override as needed
        return this.id.equals(id) ? this : null;
    }

    @Override
    public void sanitize() {
        // Override as needed
    }
    
    @Override
    public String toString() {
        return "Nav{" + "id=" + id + ", requestType=" + requestType + '}';
    }
}
