package com.example.preorderlibrary.adapters.impleAdapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.preorderlibrary.R;
import com.example.preorderlibrary.adapters.baseAdapter.MyAdapter;
import com.example.preorderlibrary.domain.QosAnsData;
import com.example.preorderlibrary.domain.QuestionData;
import com.example.preorderlibrary.views.MyView;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by willyou on 2016/12/11.
 */

public class QuestionListAdapter extends MyAdapter<List<QuestionListAdapter.CheckedQuestionData>> {
    private LayoutInflater inflater;
    private Button button;
    public QuestionListAdapter(Context context, List<List<CheckedQuestionData>> data, Button button) {
        super(context, data);
        inflater = LayoutInflater.from(context);
        this.button=button;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.disease_question_item, parent, false);
        }
        MyView myView = (MyView) convertView;
        myView.addData(data.get(position),position);
        myView.setOnAnswerClickListener(new MyView.OnAnswerClickListener() {
            @Override
            public void onAnswerItemClickListener(int index, int type) {

                if (type == MyView.TYPE_SIGLE) {
                    singleChoice(position, index, !data.get(position).get(index).isChecked);
                }else {
                    multipleChoice(position, index, !data.get(position).get(index).isChecked);
                }
                for (int i = 0; i < data.size(); i++) {
                    for (int j = 0; j < data.get(i).size(); j++) {
                        if (data.get(i).get(j).isChecked){
                            //button.setBackgroundResource(R.drawable.btnsharp);
                            notifyDataSetChanged();
                            return;
                        }
                    }
                }
            }
        });
        return convertView;
    }

    public void singleChoice(int questionPos, int index, boolean check) {
        List<CheckedQuestionData> list = data.get(questionPos);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).isChecked = false;
        }
        list.get(index).isChecked = check;
    }

    public void multipleChoice(int questionPos, int index, boolean check) {
        data.get(questionPos).get(index).setChecked(check);
    }
    public List<QosAnsData> getQuestionAnswer(){
        //List<String[]> questionAnswer=new ArrayList<>();
        List<QosAnsData> qosAnsList = new ArrayList<>();
        List<String> strList;
        for (int i = 0; i < data.size(); i++) {
            String title = "";
            strList = new ArrayList<>();
            for (int j = 1; j < data.get(i).size(); j++) {
                if (data.get(i).get(j).isChecked){
                    title = data.get(i).get(0).getQuestionData().getQuestionContent();
                    strList.add(data.get(i).get(j).getQuestionData().getQuestionContent());
                    //String[]strings={data.get(i).get(0).getQuestionData().getQuestionContent(),
                    //data.get(i).get(j).getQuestionData().getQuestionContent()};
                    //questionAnswer.add(strings);
                }
            }
            if(!TextUtils.isEmpty(title)){
                qosAnsList.add(new QosAnsData(title,strList));
            }
        }
        return qosAnsList;
    }

    public List<List<CheckedQuestionData>> getData() {
        return super.data;
    }


    public static class CheckedQuestionData {
        private boolean isChecked = false;
        private QuestionData questionData;

        public CheckedQuestionData(QuestionData questionData) {
            this.questionData = questionData;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public QuestionData getQuestionData() {
            return questionData;
        }

        public void setQuestionData(QuestionData questionData) {
            this.questionData = questionData;
        }
    }
}
