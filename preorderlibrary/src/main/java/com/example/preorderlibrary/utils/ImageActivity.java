package com.example.preorderlibrary.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.preorderlibrary.R;

/**
 * Created by willyou on 2016/11/24.
 */

public class ImageActivity extends Activity{
    private String imageAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);
        imageAddress=getIntent().getStringExtra("image");
        ImageView image=(ImageView)findViewById(R.id.preview_image);
        Button deleteBtn= (Button) findViewById(R.id.delete_btn);
        Button accessBtn= (Button) findViewById(R.id.access_btn);
        image.setImageBitmap(BitmapFactory.decodeFile(imageAddress));
        accessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("deleteMessage",imageAddress);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
