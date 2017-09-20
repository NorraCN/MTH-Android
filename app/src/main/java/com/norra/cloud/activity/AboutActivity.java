package com.norra.cloud.activity;

import android.os.Bundle;
import android.view.View;

import com.norra.cloud.R;

/**
 * Created by liurenhui on 15/2/27.
 */
public class AboutActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
