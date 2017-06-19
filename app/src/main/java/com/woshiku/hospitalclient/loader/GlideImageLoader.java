package com.woshiku.hospitalclient.loader;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.woshiku.hospitalclient.R;
import com.youth.banner.loader.ImageLoader;


public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        Glide.with(context)
                .load(path)
                .crossFade()
                .error(R.mipmap.timg)
                .into(imageView);
    }
    /*//提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
    @Override
    public ImageView createImageView(Context context) {
        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
        return simpleDraweeView;
    }*/
}
