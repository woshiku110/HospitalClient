package com.example.preorderlibrary.fragments.impleContent;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.preorderlibrary.R;
import com.example.preorderlibrary.adapters.impleAdapter.BodypartListAdapter;
import com.example.preorderlibrary.common.Global;
import com.example.preorderlibrary.domain.BodyPartData;
import com.example.preorderlibrary.domain.BodyPartMessage;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by willyou on 2016/11/25.
 */
public class BodyPartFragment extends BaseFragment implements View.OnClickListener,AdapterView.OnItemClickListener,
        CommonUrlListener {
    private CommonUrl commonUrl = new CommonUrl();
    private List<BodyPartMessage> bodyList = new ArrayList<>();
    private List<BodyPartMessage> bodyListPre=new ArrayList<>();
    private List<Map<String,Object>> bodyPartListPreBtn=new ArrayList<>();
    private BodypartListAdapter bodypartListAdapter;
    private ListView bodyPartList;
    private View lastView;
    private int position=0;
    private List<String> partsId=new ArrayList<>();

    public BodyPartFragment(FragmentActivity mActivity) {
        super(mActivity);
    }
    @Override
    public View initViews() {
        mView = View.inflate(mActivity, R.layout.fragment_bodypart,null);
        fm = (FrameLayout)mView.findViewById(R.id.fm_layout);
        bodyPartList= (ListView) mView.findViewById(R.id.bodypart_list);
        commonUrl.setCommonUrlListener(this);
        TextView head= (TextView) mView.findViewById(R.id.head);
        TextView neck= (TextView) mView.findViewById(R.id.neck);
        TextView body= (TextView) mView.findViewById(R.id.body);
        TextView leftArm= (TextView) mView.findViewById(R.id.left_arm);
        TextView rightArm= (TextView) mView.findViewById(R.id.right_arm);
        TextView leftLeg= (TextView) mView.findViewById(R.id.left_leg);
        TextView rightLeg= (TextView) mView.findViewById(R.id.right_leg);
        TextView textView= (TextView) mView.findViewById(R.id.txt_title);
        ImageButton reBackBt= (ImageButton) mView.findViewById(R.id.title_back_img);
        head.setOnClickListener(this);
        neck.setOnClickListener(this);
        body.setOnClickListener(this);
        leftArm.setOnClickListener(this);
        rightArm.setOnClickListener(this);
        leftLeg.setOnClickListener(this);
        rightLeg.setOnClickListener(this);
        reBackBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchViewPager(BODYCHOOSEFRAGMENT);
            }
        });
        head.setBackgroundResource(R.drawable.ico_choice_blue);
        head.setTextColor(ContextCompat.getColor(mActivity, R.color.blue));
        lastView = head;
        textView.setText("病症部位");
        initDatas();
        showContent();
        return mView;
    }
    private void setDefaultChoosed(){

    }
    @Override
    public void initDatas() {
        CommonUrlData commonUrlData = UrlDataUtil.getBodychoosePre(0+"", Global.token);
        commonUrl.loadUrl(commonUrlData);
    }

    public void setBodypartmessage(String bodypartmessage) {
        try {
            proccessData(bodypartmessage,bodyList);
        }catch (Exception e){
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mActivity,"服务器数据异常！",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void proccessData(String message,List<BodyPartMessage> bodyList){
        Gson gson=new Gson();
        BodyPartData bodyPartData= gson.fromJson(message,BodyPartData.class);
        getSecondBodyPartData(bodyPartData.getMsg(),bodyList);
    }
    public  void getSecondBodyPartData(String msg,List<BodyPartMessage> bodyList){
        bodyList.clear();
        Type type = new TypeToken<List<String[]>>(){}.getType();
        try{
            Gson gson = new Gson();
            List<String[]> strsList = gson.fromJson(msg,type);
            for(String[]strs:strsList){
                bodyList.add(getSecondBodyDataList(strs));
            }
            sendMessage(0,null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public BodyPartMessage getSecondBodyDataList(String[] strs){
        return new BodyPartMessage(strs[0],strs[1],strs[2],strs[3]);
    }


    @Override
    public void setFragmentAdapter() {
        bodypartListAdapter=new BodypartListAdapter(mActivity,bodyList);
        bodyPartList.setAdapter(bodypartListAdapter);
        bodyPartList.setOnItemClickListener(this);
    }

    @Override
    public void updateFragmentAdapter() {
        bodypartListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Log.e("onClick: ",v.getId()+"");
        if (v!=lastView){
            v.setBackgroundResource(R.drawable.ico_choice_blue);
            ((TextView)v).setTextColor(ContextCompat.getColor(mActivity,R.color.blue));
            if (lastView!=null){
                lastView.setBackgroundResource(R.drawable.transparent);
                ((TextView)lastView).setTextColor(ContextCompat.getColor(mActivity,R.color.black));
            }
        }
        for (int i=0;i<bodyPartListPreBtn.size();i++){
            if (((int)bodyPartListPreBtn.get(i).get("button"))==v.getId()){
                position=i;
            }
        }
        CommonUrlData commonUrlData = UrlDataUtil.getBodychoose(bodyPartListPreBtn.get(position).get("ID")+"", Global.token);
        commonUrl.loadUrl(commonUrlData);
//        ((PreOrderActivity) mActivity).openWaitDialog("正在加载数据");
        lastView=v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        ((PreOrderActivity) mActivity).openWaitDialog("正在加载数据");
        partsId.clear();
        partsId.add("\'"+bodyList.get(position).getSecondID()+"\'");
        if (partsId==null){
            return;
        }
        ((OrderDataFragment)FragmentFactory.getFragment(ORDERDATEFRAGMENT)).setPartsId(partsId+"");
        CommonUrlData commonUrlData= UrlDataUtil.getDiseaseMessage(partsId+"", Global.token);
        commonUrl.loadUrl(commonUrlData);
    }

    @Override
    public void urlResult(Result result) {
        //((PreOrderActivity)mActivity).closeWaitDialog();
        if (result.isSuccess()){
            if (result.getIntent().equals("getDiseaseMessage")){
                Log.e("BodyPartFragment->",result.getMsg());
                switchViewPager(CONCRETEFRAGMENT);
                ((ConcreteFragment)FragmentFactory.getFragment(CONCRETEFRAGMENT)).setConcreteBodyMessage(result.getMsg());
            }else if (result.getIntent().equals("bodychoose_data")){
                setBodypartmessage(result.getMsg());
            }else if (result.getIntent().equals("bodychoosePre")){
                //((PreOrderActivity)mActivity).closeWaitDialog();
                proccessData(result.getMsg(),bodyListPre);
                int[] listBtn={R.id.head,R.id.neck,R.id.body,R.id.left_arm,R.id.right_arm,R.id.left_leg,R.id.right_leg};
                for (int i=0;i<bodyListPre.size();i++){
                    Map<String,Object>map=new HashMap<>();
                    map.put("button",listBtn[i]);
                    map.put("ID",bodyListPre.get(i).getSecondID());
                    bodyPartListPreBtn.add(map);
                }
            }
        }
    }
}
