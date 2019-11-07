package com.kencorhealth.campaign.dm.rpm;

public class ClinicInfo {
    private String id;
    private ClinicType type;
    private String name;
    private String description;
    private String logoUrl;
    private String videoUrl;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    
    public ClinicType getType() {
        return type;
    }

    public void setType(ClinicType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    public String toString() {
        return
            "ClinicInfo{" + "id=" + id + ", name=" + name + ", description=" +
            description + ", logoUrl=" + logoUrl + ", type=" + type +
            ", videoUrl=" + videoUrl + '}';
    }
}
