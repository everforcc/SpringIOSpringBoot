package cn.cc.face.dao;

import java.util.Date;

public class FaceImage {
    private Integer id;
    private String personName;
    private String imagePath;
    private byte[] feature;
    private String featureJson;
    private Date createdAt;
    private Date updatedAt;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getPersonName() { return personName; }
    public void setPersonName(String personName) { this.personName = personName; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public byte[] getFeature() { return feature; }
    public void setFeature(byte[] feature) { this.feature = feature; }
    public String getFeatureJson() { return featureJson; }
    public void setFeatureJson(String featureJson) { this.featureJson = featureJson; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
} 