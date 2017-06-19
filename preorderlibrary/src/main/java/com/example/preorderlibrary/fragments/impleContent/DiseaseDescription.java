package com.example.preorderlibrary.fragments.impleContent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.preorderlibrary.PreOrderActivity;
import com.example.preorderlibrary.R;
import com.example.preorderlibrary.adapters.impleAdapter.PhotoAdapter;
import com.example.preorderlibrary.common.Global;
import com.example.preorderlibrary.common.PhotoData;
import com.example.preorderlibrary.fragments.base.BaseFragment;
import com.example.preorderlibrary.utils.CompressFileUtil;
import com.example.preorderlibrary.utils.FileUtils;
import com.example.preorderlibrary.utils.ManageThread;
import com.example.preorderlibrary.views.PhotoView;
import com.example.preorderlibrary.views.PicEditView;
import com.woshiku.albumlibrary.activity.AlbumActivity;
import com.woshiku.albumlibrary.activity.PreviewActivity;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by willyou on 2016/11/25.
 */
@SuppressLint("ValidFragment")
public class DiseaseDescription extends BaseFragment implements AdapterView.OnItemClickListener {
    private GridView photoGrid;
    private PhotoAdapter photoAdapter;
    private List<PhotoData> mList;
    private String takePhotoAddr = "";
    RelativeLayout relateTop;

    public static final int ACTION_TAKE_PICTURE = 0x000000;
    private ManageThread manageThread;
    @SuppressLint("ValidFragment")
    public DiseaseDescription(FragmentActivity mActivity) {
        super(mActivity);
    }

    @Override
    public View initViews() {
        mView = View.inflate(mActivity, R.layout.fragment_diseasedescription, null);
        relateTop = (RelativeLayout)mView.findViewById(R.id.relate_disease_top);
        photoGrid = (GridView)mView.findViewById(R.id.photosGridView);
        photoGrid.setOnItemClickListener(this);
        TextView titleText = (TextView) mView.findViewById(R.id.txt_title);
        final EditText diseaseContent = (EditText) mView.findViewById(R.id.diseaseContent);
        Button diseaseAccessBtn = (Button) mView.findViewById(R.id.diseaseAccessBtn);
        titleText.setText("症状描述");
        ((AppCompatCheckBox)mView.findViewById(R.id.disea_check_box))
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(Global.pre_type.equals("3")){//健康
                            if(isChecked){
                                ((OrderDataFragment)FragmentFactory.getFragment(2)).setOrderState(1);
                            }else{
                                ((OrderDataFragment)FragmentFactory.getFragment(2)).setOrderState(0);
                            }
                        }else{//中医 西医
                            if(isChecked){
                                ((OrderDataFragment)FragmentFactory.getFragment(ORDERDATEFRAGMENT)).setOrderState(1);
                            }else{
                                ((OrderDataFragment)FragmentFactory.getFragment(ORDERDATEFRAGMENT)).setOrderState(0);
                            }
                        }
                        Log.e("lookat",isChecked+"");
                    }
                });
        mView.findViewById(R.id.icon_return_bt)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Global.pre_type.equals("3")){//健康
                            switchHealthyViewPager(0);
                        }else{//中医 西医
                            if (Global.diseaseIsEmpty) {
                                switchViewPager(BODYPARTFRAGMENT);
                            } else {
                                switchViewPager(CONCRETEFRAGMENT);
                            }
                        }
                    }
                });
        diseaseAccessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Global.pre_type.equals("3")){//健康
                    Global.diseaseIsEmpty = true;
                }
                if(Global.diseaseIsEmpty){
                    if(Global.pre_type.equals("3")){//健康
                        ((OrderDataFragment)FragmentFactory.getFragment(2))
                                .setQuestionAnswers(null);
                        switchHealthyViewPager(2);
                        final String desc = diseaseContent.getText().toString();
                        if(mList.size()>1){
                            ((OrderDataFragment) FragmentFactory.getFragment(2))
                                    .setIsFinished(false);//压缩图片时图片没有完成
                            ((OrderDataFragment) FragmentFactory.getFragment(2))
                                    .setDesc(desc,true);//有图片
                            ((OrderDataFragment) FragmentFactory.getFragment(2))
                                    .initPicList();
                            new CompressFileUtil(mList,manageThread).setCompressResultListener(new CompressFileUtil.CompressResultListener() {
                                @Override
                                public void compressResult(int size, boolean isEq) {
                                    ((OrderDataFragment) FragmentFactory.getFragment(2))
                                            .setAmountAndFinish(size, isEq);//压缩完成 并且拿到数量
                                }
                            }).start();
                        }else{
                            ((OrderDataFragment) FragmentFactory.getFragment(2))
                                    .setDesc(desc,false);//没有图片
                        }
                    }else{//中医 心理
                        //没有疾病时
                        ((OrderDataFragment)FragmentFactory.getFragment(ORDERDATEFRAGMENT))
                                .setQuestionAnswers(null);
                        switchViewPager(ORDERDATEFRAGMENT);
                        final String desc = diseaseContent.getText().toString();
                        if(mList.size()>1){
                            ((OrderDataFragment) FragmentFactory.getFragment(ORDERDATEFRAGMENT))
                                    .setIsFinished(false);//压缩图片时图片没有完成
                            ((OrderDataFragment) FragmentFactory.getFragment(ORDERDATEFRAGMENT))
                                    .setDesc(desc,true);//有图片
                            ((OrderDataFragment) FragmentFactory.getFragment(ORDERDATEFRAGMENT))
                                    .initPicList();
                            new CompressFileUtil(mList,manageThread).setCompressResultListener(new CompressFileUtil.CompressResultListener() {
                                @Override
                                public void compressResult(int size, boolean isEq) {
                                    ((OrderDataFragment) FragmentFactory.getFragment(ORDERDATEFRAGMENT))
                                            .setAmountAndFinish(size, isEq);//压缩完成 并且拿到数量
                                }
                            }).start();
                        }else{
                            ((OrderDataFragment) FragmentFactory.getFragment(ORDERDATEFRAGMENT))
                                    .setDesc(desc,false);//没有图片
                        }
                    }

                }else{
                    switchViewPager(DISEASEQUESTION);
                    final String desc = diseaseContent.getText().toString();
                    if(mList.size()>1){
                        ((OrderDataFragment) FragmentFactory.getFragment(ORDERDATEFRAGMENT))
                                .setIsFinished(false);//压缩图片时图片没有完成
                        ((OrderDataFragment) FragmentFactory.getFragment(ORDERDATEFRAGMENT))
                                .setDesc(desc, true);//有图片
                        ((OrderDataFragment) FragmentFactory.getFragment(ORDERDATEFRAGMENT))
                                .initPicList();
                        new CompressFileUtil(mList,manageThread).setCompressResultListener(new CompressFileUtil.CompressResultListener() {
                            @Override
                            public void compressResult(int size, boolean isEq) {
                                ((OrderDataFragment) FragmentFactory.getFragment(ORDERDATEFRAGMENT))
                                        .setAmountAndFinish(size, isEq);//压缩完成 并且拿到数量
                            }
                        }).start();
                    }else{
                        ((OrderDataFragment) FragmentFactory.getFragment(ORDERDATEFRAGMENT))
                                .setDesc(desc, false);//没有图片
                    }
                }
            }
        });
        initDatas();
        return mView;
    }

    public void initDatas(){
        manageThread = ManageThread.getInstance();
        manageThread.createCompressedThread(5);
        mList = new ArrayList<>();
        mList.add(0, new PhotoData(0, null));
        photoAdapter = new PhotoAdapter(mActivity,mList);
        sendMessage(0, null);
    }
    @Override
    public void setFragmentAdapter() {
        photoGrid.setAdapter(photoAdapter);
    }

    @Override
    public void updateFragmentAdapter() {
        photoAdapter.notifyDataSetChanged();
    }
    /**
     * @param photoDataList 添加照片数据列表
     * */
    public void addListData(List<PhotoData> photoDataList){
        mList.addAll(photoDataList);
        photoAdapter.setData(mList);
        sendMessage(1, null);
    }

    /**
     * 拍照
     * */
    public void takePhoto(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                openCameraIntent.addCategory("android.intent.category.DEFAULT");
                takePhotoAddr = FileUtils.generImageName();
                File file = new File(takePhotoAddr);
                Uri imageUri = Uri.fromFile(file);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                mActivity.startActivityForResult(openCameraIntent, ACTION_TAKE_PICTURE);
            }
        }.start();
    }
    /**
     * 拍照是否成功
     * */
    public void photoIsOk(boolean success){
        if(success){
            //拍照成功后加入数据库
            final File file = new File(takePhotoAddr);
            if(file.exists()){
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        mList.add(new PhotoData(1,takePhotoAddr));
                        photoAdapter.setData(mList);
                        sendMessage(1, null);
                        mActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.fromFile(file)));
                    }
                }.start();
            }else{
                takePhotoAddr = "";
            }
        }else{
            takePhotoAddr = "";
        }
    }
    /**
     * @param //type 类型为添加类
     * @param //albumChoose 从相册选择图片
     * @param //albumPhoto 拍照图片
     * */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if(mList.get(position).type == 0){
            new PhotoView(mActivity,relateTop).addAlbumChooseListener(new PhotoView.AlbumChooseListener() {
                @Override
                public void albumChoose() {
                    mActivity.startActivity(new Intent(mActivity, AlbumActivity.class));
                }
            }).addAlbumPhotoListener(new PhotoView.AlbumPhotoListener() {
                @Override
                public void albumPhoto(){
                    PreOrderActivity preOrderActivity = (PreOrderActivity) mActivity;
                    if (preOrderActivity.isAllowPhoto()) {
                        takePhoto();
                    } else {
                        preOrderActivity.setTakePhoto();
                    }
                }
            }).showDialog();
        }else{
            final String picPath = mList.get(position).imageAddr;
            new PicEditView(mActivity,relateTop).addAlbumPreviewListener(new PicEditView.AlbumPreviewListener() {
                @Override
                public void previewPhoto() {
                    File file = new File(picPath);
                    if(file.exists()){
                        Intent it = new Intent(mActivity, PreviewActivity.class);
                        it.putExtra(PreviewActivity.filePath, picPath);
                        mActivity.startActivity(it);
                    }
                }
            }).addAlbumDeleListener(new PicEditView.AlbumDeleListener() {
                @Override
                public void delePhoto() {
                    mList.remove(position);
                    sendMessage(1,null);
                    FileUtils.deleFile(mActivity,picPath,photoAdapter.getBitmapUtils());
                }
            }).showDialog();
        }
    }
}
