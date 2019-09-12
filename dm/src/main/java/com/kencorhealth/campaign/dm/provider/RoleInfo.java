package com.kencorhealth.campaign.dm.provider;

public class RoleInfo {
    private Role role;
    private String extra;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
    
    public static RoleInfo from(String roleName) {
        RoleInfo retVal = new RoleInfo();
        
        retVal.role = Role.valueOf(roleName);
        
        return retVal;
    }
    
    @Override
    public String toString() {
        return "RoleInfo{" + "role=" + role + ", extra=" + extra + '}';
    }
}
