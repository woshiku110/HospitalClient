package com.example.preorderlibrary.domain;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */
public class QosAnsData {
    public String question;
    public List<String> answers;

    public QosAnsData(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }
}
