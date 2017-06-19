package com.example.preorderlibrary.domain;

/**
 * Created by willyou on 2016/12/9.
 */

public class QuestionData {
    private String questionId;
    private String questionContent;

    @Override
    public String toString() {
        return "QuestionData{" +
                "questionId='" + questionId + '\'' +
                ", questionContent='" + questionContent + '\'' +
                '}';
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public QuestionData() {

    }

    public QuestionData(String questionId, String questionContent) {

        this.questionId = questionId;
        this.questionContent = questionContent;
    }
}
