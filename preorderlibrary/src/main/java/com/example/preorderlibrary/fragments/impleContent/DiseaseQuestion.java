package com.example.preorderlibrary.fragments.impleContent;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.preorderlibrary.R;
import com.example.preorderlibrary.adapters.impleAdapter.QuestionListAdapter;
import com.example.preorderlibrary.domain.DiseaseQuestionData;
import com.example.preorderlibrary.domain.DiseaseQuestionListData;
import com.example.preorderlibrary.domain.QuestionData;
import com.example.preorderlibrary.fragments.base.BaseFragment;
import com.example.preorderlibrary.views.MyView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.inter.CommonUrlListener;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by willyou on 2016/12/6.
 */
public class DiseaseQuestion extends BaseFragment implements CommonUrlListener {
    MyView myView;
    private List<DiseaseQuestionData> diseaseQuestionData=new ArrayList<>();
    private CommonUrl commonUrl = new CommonUrl();
    private Button questionSubmitBtn;
    List<QuestionData>questionDatas=new ArrayList<>();

    List<List<QuestionData>>questionList=new ArrayList<>();

    ListView questionListView;
    QuestionListAdapter questionListAdapter;
    public DiseaseQuestion(FragmentActivity mActivity) {
        super(mActivity);
    }

    @Override
    public void setFragmentAdapter() {
        List<List<QuestionListAdapter.CheckedQuestionData>> datas = new ArrayList<>(questionList.size());
        for (int i = 0; i < questionList.size(); i++) {
            List<QuestionData> tempList = questionList.get(i);
            List<QuestionListAdapter.CheckedQuestionData> list = new ArrayList<>(tempList.size());
            for (int j = 0; j < tempList.size(); j++) {
                list.add(new QuestionListAdapter.CheckedQuestionData(tempList.get(j)));
            }
            datas.add(list);
        }
        questionListAdapter = new QuestionListAdapter(mActivity, datas,questionSubmitBtn);
        questionListView.setAdapter(questionListAdapter);
    }

    @Override
    public void updateFragmentAdapter() {

    }
    @Override
    public View initViews() {
        mView = View.inflate(mActivity, R.layout.fragment_diseasequestion,null);
        fm = (FrameLayout)mView.findViewById(R.id.fm_layout);
        TextView titleText= (TextView) mView.findViewById(R.id.txt_title);
        questionListView= (ListView) mView.findViewById(R.id.question_list);
        questionSubmitBtn= (Button) mView.findViewById(R.id.submit_question);
        questionSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchViewPager(ORDERDATEFRAGMENT);
                ((OrderDataFragment) FragmentFactory.getFragment(ORDERDATEFRAGMENT))
                        .setQuestionAnswers(new Gson().toJson(questionListAdapter.getQuestionAnswer()));
            }
        });
        mView.findViewById(R.id.icon_return_bt)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switchViewPager(DISEASEDESCROPTION);
                    }
                });
        titleText.setText("问诊问题");
        showContent();
        return mView;
    }

    public void setDiseaseQuestionDatas(String msg) {
        processData(msg);
        questionList.clear();
        for (int i=0;i<diseaseQuestionData.size();i++){
            processQuestion(diseaseQuestionData.get(i).getQuestion());
            questionList.add(questionDatas);
        }
        sendMessage(0, null);
    }
    public void processQuestion(String question){
        questionDatas = new ArrayList<>();
        Type type = new TypeToken<List<String[]>>(){}.getType();
        try{
            Gson gson = new Gson();
            List<String[]> strsList = gson.fromJson(question, type);
            for(String[] strs:strsList){
                questionDatas.add(processDataSec(strs));
            }
            Log.e("问题数组->",questionDatas.toString());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public QuestionData processDataSec(String[] strs){
        return new QuestionData(strs[0],strs[1]);
    }

    public void processData(String msg){
        Gson gson=new Gson();
        DiseaseQuestionListData diseaseQuestionListData=gson.fromJson(msg,DiseaseQuestionListData.class);
        Log.e("问题字符串->",diseaseQuestionListData.getMsg());
        processDataSec(diseaseQuestionListData.getMsg());
    }
    public void processDataSec(String msg){
        diseaseQuestionData.clear();
        Type type = new TypeToken<List<String>>(){}.getType();
        try{
            Gson gson = new Gson();
            List<String> strsList = gson.fromJson(msg, type);
            for(String strs:strsList){
                diseaseQuestionData.add(processDataThi(strs));
            }
            Log.e("问题队列->",diseaseQuestionData.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public DiseaseQuestionData processDataThi(String strs){
        return new DiseaseQuestionData(strs);
    }

    @Override
    public void urlResult(Result result) {
        if (result.isSuccess()){
        }
    }
}
