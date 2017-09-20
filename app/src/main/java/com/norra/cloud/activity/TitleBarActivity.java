package com.norra.cloud.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.norra.cloud.R;

/**
 * Created by liurenhui on 15/2/4.
 */
public class TitleBarActivity extends BaseActivity {
    private TextView titleView;
    private Button leftBtn;
    private Button rightBtn;
    private FrameLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBaseViews();
    }

    @Override
    public void setContentView(int layoutResID) {
        initBaseViews();
        View view = getLayoutInflater().inflate(layoutResID, null);
        contentView.addView(view);
    }

    @Override
    public void setContentView(View view) {
        initBaseViews();
        contentView.removeAllViews();
        contentView.addView(view);
    }

    private void initBaseViews() {
        if (contentView == null) {
            super.setContentView(R.layout.activity_title_bar);
            titleView = (TextView) findViewById(R.id.main_title);
            leftBtn = (Button) findViewById(R.id.left_btn);
            rightBtn = (Button) findViewById(R.id.right_btn);
            contentView = (FrameLayout) findViewById(R.id.content_view);
            leftBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    public void setTitle(String title) {
        initBaseViews();
        titleView.setText(title);
    }

    public void setTitle(int resId) {
        initBaseViews();
        titleView.setText(resId);
    }

    public Button getLeftButton() {
        return this.leftBtn;
    }

    public Button getRightButton() {
        return this.rightBtn;
    }

}
