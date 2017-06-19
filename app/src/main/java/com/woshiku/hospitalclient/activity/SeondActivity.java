package com.woshiku.hospitalclient.activity;

import com.woshiku.hospitalclient.R;
import com.woshiku.hospitalclient.dispatchactivity.BaseActivity;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
public class SeondActivity extends BaseActivity {
    private SwipeBackLayout swipeBackLayout;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_seond);
        swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setGesture(true);//设置可以滑动
    }

    @Override
    protected void allowPhoto() {

    }

    @Override
    protected void allowWrite() {

    }

    @Override
    public void swipeBackCallback() {

    }
}
