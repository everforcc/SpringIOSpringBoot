package cn.cc.face.dao;

import java.util.Date;

public class FaceRecognitionLog {
    private Integer id;
    private Integer imageId;
    private String inputImagePath;
    private String result;
    private Float similarity;
    private Date recognizedAt;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getImageId() { return imageId; }
    public void setImageId(Integer imageId) { this.imageId = imageId; }
    public String getInputImagePath() { return inputImagePath; }
    public void setInputImagePath(String inputImagePath) { this.inputImagePath = inputImagePath; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public Float getSimilarity() { return similarity; }
    public void setSimilarity(Float similarity) { this.similarity = similarity; }
    public Date getRecognizedAt() { return recognizedAt; }
    public void setRecognizedAt(Date recognizedAt) { this.recognizedAt = recognizedAt; }
} 