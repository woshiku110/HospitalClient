package com.example.preorderlibrary.domain;

/**
 * Created by willyou on 2016/12/7.
 */

public class DiseaseQuestionData {
    private String question;

    public DiseaseQuestionData() {
    }

    public DiseaseQuestionData(String question) {

        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "DiseaseQuestionData{" +
                "question='" + question + '\'' +
                '}';
    }
}
