package com.example.preorderlibrary.fragments.impleContent;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.preorderlibrary.PreOrderActivity;
import com.example.preorderlibrary.R;
import com.example.preorderlibrary.adapters.impleAdapter.ContactListAdapter;
import com.example.preorderlibrary.common.Global;
import com.example.preorderlibrary.domain.PersonData;
import com.example.preorderlibrary.domain.PersonDataPre;
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
public class ContactFragment extends BaseFragment implements CommonUrlListener {
    private CommonUrl commonUrl = new CommonUrl();
    private List<PersonData> mList = new ArrayList<>();
    private ContactListAdapter contactListAdapter;
    private ListView contact_layout_list = null;
    @SuppressLint("ValidFragment")
    public ContactFragment(FragmentActivity mActivity) {
        super(mActivity);
    }

    @Override
    public View initViews() {
        mView = View.inflate(mActivity, R.layout.fragment_contact, null);
        fm = (FrameLayout) mView.findViewById(R.id.fm_layout);
        contact_layout_list = (ListView) mView.findViewById(R.id.contact_layout_list);
        TextView textView = (TextView) mView.findViewById(R.id.txt_title);
        textView.setText("选择用户");
        mView.findViewById(R.id.icon_return_bt)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PreOrderActivity mainUi = (PreOrderActivity) mActivity;
                        mainUi.scrollToFinishActivity();
                    }
                });
        Button button = (Button) mView.findViewById(R.id.btn_reload);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatas();
            }
        });
        initDatas();
        return mView;
    }

    public void initDatas() {
        showWait();
        commonUrl.setCommonUrlListener(this);
        CommonUrlData commonUrlData = UrlDataUtil.getContactData(Global.token);
        commonUrl.loadUrl(commonUrlData);
    }

    @Override
    public void urlResult(Result result) {
        sendMessage(SHOW_CONTENT, null);
        if (result.isSuccess()) {
            if (result.getIntent().equals("contact_data")) {
                Log.e("lookat", result.getMsg());
                processData(result.getMsg());
                getPersonList(result.getMsg());
                if (contactListAdapter == null) {
                    sendMessage(0, null);
                } else {
                    sendMessage(1, null);
                }
            }
        } else {
            sendMessage(SHOW_FAIL, null);
        }
    }

    public void processData(String message) {
        Gson gson = new Gson();
        PersonDataPre personDataPre = gson.fromJson(message, PersonDataPre.class);
        getPersonList(personDataPre.msg);
    }

    ;

    public void getPersonList(String msg) {
        Type type = new TypeToken<List<String[]>>() {}.getType();
        try {
            Gson gson = new Gson();
            List<String[]> strsList = gson.fromJson(msg, type);
            for (String[] strs : strsList) {
                mList.add(getPersonData(strs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PersonData getPersonData(String[] strs) {
        Log.e("lookat","sex:"+strs[3]);
        return new PersonData(strs[0], strs[1], strs[2], strs[3], strs[4], strs[5]);
    }


    @Override
    public void setFragmentAdapter() {
        final List<PersonData> allList = new ArrayList<>();
        PersonData personData = new PersonData();
        personData.setTypeOne(false);
        allList.addAll(mList);
        allList.add(personData);
        contactListAdapter = new ContactListAdapter(mActivity, allList);
        contact_layout_list.setAdapter(contactListAdapter);
        contact_layout_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < (allList.size() - 1)) {
                    if(Global.pre_type.equals("3")){//健康
                        switchHealthyViewPager(1);//图文描述
                        ((OrderDataFragment)FragmentFactory.getFragment(2))
                                .setSubmitPersonId(allList.get(position).getId());
                    }else{//中医 西医
                        if(mList.get(position).getSex().equals("男")){
                            ((BodyChooseFragment)FragmentFactory.getFragment(BODYCHOOSEFRAGMENT)).loadUrl(true);
                        }else{
                            ((BodyChooseFragment)FragmentFactory.getFragment(BODYCHOOSEFRAGMENT)).loadUrl(false);
                        }
                        switchViewPager(BODYCHOOSEFRAGMENT);
                        ((OrderDataFragment)FragmentFactory.getFragment(ORDERDATEFRAGMENT))
                                .setSubmitPersonId(allList.get(position).getId());
                    }
                } else {
                    PreOrderActivity mainUi = (PreOrderActivity)mActivity;
                    mainUi.enterNewFamily();
                }
            }
        });
        showContent();
    }

    @Override
    public void updateFragmentAdapter() {
        List<PersonData> allList = new ArrayList<>();
        PersonData personData = new PersonData();
        personData.setTypeOne(false);
        allList.addAll(mList);
        allList.add(personData);
        contactListAdapter.notifyDataSetChanged();
        showContent();
    }
    public void updateData(final List<PersonData> mList){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                contactListAdapter.setData(mList);
                contactListAdapter.notifyDataSetChanged();
            }
        });
    }
}
