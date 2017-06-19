package com.example.preorderlibrary.fragments.impleContent;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import com.example.preorderlibrary.R;
import com.example.preorderlibrary.adapters.impleAdapter.ConcreteAdapter;
import com.example.preorderlibrary.common.Global;
import com.example.preorderlibrary.domain.ConcreteBodyPartData;
import com.example.preorderlibrary.domain.ConcreteBodyPartMessage;
import com.example.preorderlibrary.fragments.base.BaseFragment;
import com.example.preorderlibrary.utils.UrlDataUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woshiku.urllibrary.domain.CommonUrlData;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.inter.CommonUrlListener;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by willyou on 2016/11/25.
 */
@SuppressLint("ValidFragment")
public class ConcreteFragment extends BaseFragment implements CommonUrlListener{
    private List<ConcreteBodyPartMessage> concreteList = new ArrayList<>();
    private CommonUrl commonUrl = new CommonUrl();
    private ConcreteAdapter concreteAdapter;
    private GridView concrete_bodypart_gridview;
    private Button concreteBt;
    @SuppressLint("ValidFragment")
    public ConcreteFragment(FragmentActivity mActivity) {
        super(mActivity);
    }
    @Override
    public View initViews() {
        mView = View.inflate(mActivity, R.layout.fragment_concretebodypart,null);
        fm = (FrameLayout)mView.findViewById(R.id.fm_layout);
        commonUrl.setCommonUrlListener(this);
        concreteBt = (Button)mView.findViewById(R.id.concrete_bt);
        TextView titleText= (TextView) mView.findViewById(R.id.txt_title);
        concrete_bodypart_gridview=(GridView)mView.findViewById(R.id.concrete_bodypart_gridview);
        mView.findViewById(R.id.icon_return_bt)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switchViewPager(BODYPARTFRAGMENT);
                    }
                });
        concreteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isSelectedTrue = concreteAdapter.getIsSelectedTrue();
                ((OrderDataFragment) FragmentFactory.getFragment(ORDERDATEFRAGMENT)).setDiseaseImageIds(isSelectedTrue);
                //打开等待对话框
                openDialog();
                CommonUrlData commonUrlData = UrlDataUtil.getDiseaseQuestionList(isSelectedTrue, Global.token);
                commonUrl.loadUrl(commonUrlData);
            }
        });
        titleText.setText("症状选择");
        showContent();
        return mView;
    }


    public void setConcreteBodyMessage(String concreteBodyMessage) {
        processData(concreteBodyMessage);
    }
    private void processData(String message) {
        Gson gson=new Gson();
        ConcreteBodyPartData concretebodypartdata = gson.fromJson(message, ConcreteBodyPartData.class);
        getResConcreteBodyPartData(concretebodypartdata.msg);
    }
    private void getResConcreteBodyPartData(String message) {
        concreteList.clear();
        Type type = new TypeToken<List<String[]>>(){}.getType();
        try{
            Gson gson = new Gson();
            List<String[]> strsList = gson.fromJson(message, type);
            for(String[]strs:strsList){
                concreteList.add(getSecondConcreteList(strs));
            }
            sendMessage(0,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(concreteList.size()==0){
            Global.diseaseIsEmpty = true;
            switchViewPager(DISEASEDESCROPTION);
        }else{
            Global.diseaseIsEmpty = false;
        }
    }

    private ConcreteBodyPartMessage getSecondConcreteList(String[] strs) {
        return new ConcreteBodyPartMessage(strs[0],strs[1],strs[2]);
    }

    @Override
    public void setFragmentAdapter() {
        if (concreteAdapter==null){
            concreteAdapter=new ConcreteAdapter(mActivity,concreteList,concreteBt);
            concreteAdapter.setChooseSymbolListener(new ConcreteAdapter.ChooseSymbolListener() {
                @Override
                public void symbolChoose(String userSelected) {
                    if(userSelected!=null){
                        concreteBt.setVisibility(View.VISIBLE);
                    }else{
                        concreteBt.setVisibility(View.INVISIBLE);
                    }
                    Log.e("lookat","user choose"+userSelected);
                }
            });
            concreteAdapter.initState();
            concrete_bodypart_gridview.setAdapter(concreteAdapter);
        }
        else {
            sendMessage(1,null);
        }
    }
    @Override
    public void updateFragmentAdapter() {
        concreteAdapter.refresh(concreteList);
    }

    @Override
    public void urlResult(Result result) {
        //关闭对话框
        closeDialog();
        if (result.isSuccess()){
            if (result.getIntent().equals("getDiseaseQuestion")){
                Log.e("diseaseQuestion请求结果", result.getMsg());
                ((DiseaseQuestion) FragmentFactory.createContentFragment(getActivity(),
                        DISEASEQUESTION)).setDiseaseQuestionDatas(result.getMsg());
                switchViewPager(DISEASEDESCROPTION);
            }
        }
    }
}
