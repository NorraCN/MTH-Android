package com.norra.cloud.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.norra.cloud.R;
import com.norra.cloud.api.API;
import com.norra.cloud.api.response.BaseResponse;
import com.norra.cloud.api.response.DeviceDetailResponse;
import com.norra.cloud.entity.DataItem2;
import com.norra.cloud.entity.Device;
import com.norra.cloud.http.frame.HError;
import com.norra.cloud.http.frame.ResponseListener;
import com.norra.cloud.utils.Toaster;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.norra.cloud.R.id.power;

/**
 * Created by liurenhui on 15/1/22.
 * Modified by shenletao after 16/7/1
 */
public class DeviceDetailActivity extends TitleBarActivity implements View.OnClickListener {
    private Resources rsrc;
    private TextView data1;
    private TextView data1Value;
    private TextView data2;
    private TextView data2Value;
    private TextView data3;
    private TextView data3Value;
    private TextView data4;
    private TextView data4Value;
    private RelativeLayout layout3;
    private RelativeLayout layout4;
    private TextView powerView;
    private TextView temRangeView;
    private TextView humRangeView;
    private View viewHistoryDataView;
    private Integer deviceId;
    private TextView time;
    private TextView temDesc;
    private TextView humDesc;
    private ImageView checkBox;
    private boolean closeAlarm = false;
    private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int NORMAL;
    private int HIGH;
    private int LOW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rsrc = getResources();
        NORMAL = rsrc.getColor(R.color.theme);
        HIGH = rsrc.getColor(R.color.red);
        LOW = rsrc.getColor(R.color.gray_bc);

        Object obj = getIntent().getSerializableExtra("deviceId");
        if (obj == null) {
            finish();
            return;
        }
        deviceId = (Integer) obj;

        getRightButton().setVisibility(View.VISIBLE);
        getRightButton().setBackgroundResource(R.drawable.refresh);
        getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceDetail();
            }
        });

        setContentView(R.layout.activity_device);

        data1 = (TextView) findViewById(R.id.detail_data1);
        data1Value = (TextView) findViewById(R.id.detail_data1_value);
        data2 = (TextView) findViewById(R.id.detail_data2);
        data2Value = (TextView) findViewById(R.id.detail_data2_value);
        data3 = (TextView) findViewById(R.id.detail_data3);
        data3Value = (TextView) findViewById(R.id.detail_data3_value);
        data4 = (TextView) findViewById(R.id.detail_data4);
        data4Value = (TextView) findViewById(R.id.detail_data4_value);
        powerView = (TextView) findViewById(power);
        viewHistoryDataView = findViewById(R.id.view_history_data);
        //layout3 = (RelativeLayout) findViewById(R.id.detail_layout3);
        //layout4 = (RelativeLayout) findViewById(R.id.detail_layout4);
        //temRangeView = (TextView) findViewById(R.id.temperature_range);
        //humRangeView = (TextView) findViewById(R.id.humidity_range);
        checkBox = (ImageView) findViewById(R.id.kai_guan);
        //temDesc = (TextView) findViewById(R.id.temperature_desc);
        //humDesc = (TextView) findViewById(R.id.humidity_desc);
        viewHistoryDataView.setOnClickListener(this);
        checkBox.setOnClickListener(this);
        time = (TextView) findViewById(R.id.time);
        getDeviceDetail();
    }

    private void getDeviceDetail() {
        API.getDeviceDetail(this, String.valueOf(deviceId), new ResponseListener<DeviceDetailResponse>() {
            @Override
            public void onSuccess(DeviceDetailResponse response) {
                updateDeviceInfo(response);
            }

            @Override
            public void onError(HError error) {
                if (error.isTokenExpiredError()) {
                    onTokenExpired();
                }
            }
        });

    }

    private void updateDeviceInfo(DeviceDetailResponse response) {
        if (!response.getDevice().getAlarmSwitch()) {
            checkBox.setBackgroundResource(R.drawable.rb_unchecked);
            closeAlarm = true;
        } else {
            checkBox.setBackgroundResource(R.drawable.rb_checked);
            closeAlarm = false;
        }
        checkBox.setVisibility(View.VISIBLE);

        Device device = response.getDevice();
        List<DataItem2> list = response.getLastFrame();

        int i = 1;
        for(DataItem2 dItem : list){
            try {
                Method fGet = this.getClass().getDeclaredMethod("getData" + String.valueOf(i));
                Method fSet = this.getClass().getDeclaredMethod("setData" + String.valueOf(i), TextView.class);
                Method fGetV = this.getClass().getDeclaredMethod("getData" + String.valueOf(i)+"Value");
                Method fSetV = this.getClass().getDeclaredMethod("setData" + String.valueOf(i)+"Value", TextView.class);
                TextView dataText = (TextView)fGet.invoke(this);
                TextView valueText = (TextView)fGetV.invoke(this);

                if(dItem.getAlert() == 0){
                    dataText.setTextColor(NORMAL);
                    valueText.setTextColor(NORMAL);
                }
                else if(dItem.getAlert() == 1){
                    dataText.setTextColor(HIGH);
                    valueText.setTextColor(HIGH);
                }
                else {
                    dataText.setTextColor(HIGH);
                    valueText.setTextColor(LOW);
                }
                dataText.setText(dItem.getName()+"("+dItem.getUnit()+"): ");
                valueText.setText(dItem.getValue() + setRange(dItem.getMin(), dItem.getMax()));

                fSet.invoke(this, dataText);
                fSetV.invoke(this, valueText);
                ++i;
            } catch (Exception e){
                System.out.println(e);
            }
        }



//      layout3.setVisibility(View.INVISIBLE);

        String power = device.getEnergy() + "%";
        if (device.getMinEnergy() != null && device.getEnergy() < device.getMinEnergy()) {
            powerView.setTextColor(HIGH);
        }
        powerView.setText(power + setRange(device.getMinEnergy(), null));

        time.setText(sdf.format(new Date(device.getTime()*1000)));
        if(device.getName() == null || device.getName().trim().equals(""))
            setTitle(device.getIdentifier().toString());
        else setTitle(device.getIdentifier()+" "+device.getName());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == viewHistoryDataView.getId()) {
            Intent intent = new Intent(this, HistoryDataActivity.class);
            intent.putExtra("device_id", String.valueOf(deviceId));
            startActivity(intent);
        } else if (v.getId() == checkBox.getId()) {
            changeAlarmStatue();
        }
    }

    private void changeAlarmStatue() {
        API.openCloseAlarm(this, !closeAlarm, String.valueOf(deviceId), new ResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                Toaster.shortToast(R.string.set_success);
                closeAlarm = !closeAlarm;
                if (closeAlarm) {
                    checkBox.setBackgroundResource(R.drawable.rb_unchecked);
                } else {
                    checkBox.setBackgroundResource(R.drawable.rb_checked);
                }
            }

            @Override
            public void onError(HError error) {
                if (error.isTokenExpiredError()) {
                    onTokenExpired();
                } else {
                    Toaster.shortToast(R.string.set_fail);
                }
            }
        });
    }

    private String setRange(Float min, Float max){
        if(min == null && max == null) return "";
        String range = " ~ ";
        range = " ("+(min==null?"    ":min) + range + (max==null?"    ":max)+")";
        return range;
    }

    public TextView getData1() {
        return data1;
    }

    public void setData1(TextView data1) {
        this.data1 = data1;
    }

    public TextView getData1Value() {
        return data1Value;
    }

    public void setData1Value(TextView data1Value) {
        this.data1Value = data1Value;
    }

    public TextView getData2() {
        return data2;
    }

    public void setData2(TextView data2) {
        this.data2 = data2;
    }

    public TextView getData2Value() {
        return data2Value;
    }

    public void setData2Value(TextView data2Value) {
        this.data2Value = data2Value;
    }

    public TextView getData3() {
        return data3;
    }

    public void setData3(TextView data3) {
        this.data3 = data3;
    }

    public TextView getData3Value() {
        return data3Value;
    }

    public void setData3Value(TextView data3Value) {
        this.data3Value = data3Value;
    }

    public TextView getData4() {
        return data4;
    }

    public void setData4(TextView data4) {
        this.data4 = data4;
    }

    public TextView getData4Value() {
        return data4Value;
    }

    public void setData4Value(TextView data4Value) {
        this.data4Value = data4Value;
    }
}
