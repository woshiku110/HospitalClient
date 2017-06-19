package com.example.preorderlibrary.domain;

/**
 * Created by willyou on 2016/11/15.
 */

public class ConcreteBodyPartMessage {
    private String diseaseId;
    private String diseaseTitle;
    private String diseasePic;
    public ConcreteBodyPartMessage(){}

    public ConcreteBodyPartMessage(String diseaseId, String diseaseTitle) {
        this.diseaseId = diseaseId;
        this.diseaseTitle = diseaseTitle;
    }

    public ConcreteBodyPartMessage(String diseaseId, String diseaseTitle,String diseasePic) {
        this.diseaseId = diseaseId;
        this.diseaseTitle = diseaseTitle;
        this.diseasePic = diseasePic;
    }

    @Override
    public String toString() {
        return "ConcreteBodyPartMessage{" +
                "diseaseId='" + diseaseId + '\'' +
                ", diseaseTitle='" + diseaseTitle + '\'' +
                '}';
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDiseaseTitle() {
        return diseaseTitle;
    }

    public void setDiseaseTitle(String diseaseTitle) {
        this.diseaseTitle = diseaseTitle;
    }

    public String getDiseasePic() {
        return diseasePic;
    }

    public void setDiseasePic(String diseasePic) {
        this.diseasePic = diseasePic;
    }
}
