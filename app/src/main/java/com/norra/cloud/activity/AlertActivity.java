package com.norra.cloud.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.norra.cloud.R;
import com.norra.cloud.receiver.MyReceiver.PushInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liurenhui on 15/2/3.
 * Modified by shenletao after 16/7/1
 */
public class AlertActivity extends TitleBarActivity implements View.OnClickListener {
    PushInfo pushInfo;
    private TextView dataView;
//    private TextView data2View;
//    private TextView data3View;
//    private TextView data4View;
    private TextView energyView;
    private TextView timeView;
    private TextView deviceNameView;
    private Button okBtn;
    private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Resources rsrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.data_error);
        setContentView(R.layout.activity_alert);
        Object obj = getIntent().getSerializableExtra("push_info");
        if (obj == null) {
            finish();
            return;
        }
        pushInfo = (PushInfo) obj;

        initView();
        setValue();

    }

    private void setValue() {
        StringBuilder alarmInfo = new StringBuilder();
        if(pushInfo.state1!=null)alarmInfo.append(pushInfo.state1+"\n");
        if(pushInfo.state2!=null)alarmInfo.append(pushInfo.state2+"\n");
        if(pushInfo.state3!=null)alarmInfo.append(pushInfo.state3+"\n");
        if(pushInfo.state4!=null)alarmInfo.append(pushInfo.state4+"\n");
        dataView.setText(alarmInfo.toString());
        energyView.setText(pushInfo.stateE!=null?"电量不足":"");

//        int state = pushInfo.state;
//        if (state > 0) {
//            if((state & 1) == 1)data1View.setTextColor(getResources().getColor(R.color.red));
//            else if((state & 2) == 2)data1View.setTextColor(getResources().getColor(R.color.gray_bc));
//            else data1View.setTextColor(getResources().getColor(R.color.theme));
//
//            if((state & 4) == 4)data2View.setTextColor(getResources().getColor(R.color.red));
//            else if((state & 8) == 8)data2View.setTextColor(getResources().getColor(R.color.gray_bc));
//            else data2View.setTextColor(getResources().getColor(R.color.theme));
//
//            if((state & 16) == 16)energyView.setTextColor(getResources().getColor(R.color.gray_bc));
//            else energyView.setTextColor(getResources().getColor(R.color.theme));
//            //lastData.setTextColor(getContext().getResources().getColor(R.color.red));
//
//            if((state & 32) == 32)data3View.setTextColor(getResources().getColor(R.color.red));
//            else if((state & 64) == 64)data3View.setTextColor(getResources().getColor(R.color.gray_bc));
//            else data3View.setTextColor(getResources().getColor(R.color.theme));
//
//            if((state & 128) == 128)data4View.setTextColor(getResources().getColor(R.color.red));
//            else if((state & 256) == 256)data4View.setTextColor(getResources().getColor(R.color.gray_bc));
//            else data4View.setTextColor(getResources().getColor(R.color.theme));
//        }

        timeView.setText(sdf.format(new Date(pushInfo.time)));
        deviceNameView.setText(pushInfo.name);
    }

    private void initView() {
        rsrc = getResources();
        dataView = (TextView) findViewById(R.id.alert_data);
        dataView.setTextColor(rsrc.getColor(R.color.red));
//        data2View = (TextView) findViewById(R.id.alert_data2);
//        data3View = (TextView) findViewById(R.id.alert_data3);
//        data4View = (TextView) findViewById(R.id.alert_data4);
        energyView = (TextView) findViewById(R.id.power);
        timeView = (TextView) findViewById(R.id.alert_time);
        deviceNameView = (TextView) findViewById(R.id.device_name);
        okBtn = (Button) findViewById(R.id.ok);
        okBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == okBtn.getId()) {
            finish();
        }
    }
}
