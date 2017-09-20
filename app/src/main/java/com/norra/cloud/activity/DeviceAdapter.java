package com.norra.cloud.activity;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.norra.cloud.R;
import com.norra.cloud.adapter.ListAdapter;
import com.norra.cloud.entity.DataItem;
import com.norra.cloud.entity.DeviceItem;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/1/19.
 * Modified by shenletao after 16/7/1
 */
public class DeviceAdapter extends ListAdapter<DeviceItem> {

    public DeviceAdapter(Context context) {
        super(context);
    }
    private Resources rsrc = getContext().getResources();
    private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public int NORMAL = rsrc.getColor(R.color.theme);
    public int HIGH = rsrc.getColor(R.color.red);
    public int LOW = rsrc.getColor(R.color.gray_bc);
    TextView deviceNameView;
    TextView data1;
    TextView data2;
    TextView data3;
    TextView data4;
    TextView energy;
    TextView time;
    TextView deviceIcon;
    static int TOTAL = 4;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DeviceItem item = getItem(position);

        if (convertView == null) {
            convertView = inflaterView(R.layout.device_list_item);


        }
        deviceNameView = (TextView) convertView.findViewById(R.id.device_name);
//        data1 = (TextView) convertView.findViewById(R.id.device_lastData1);
//        data2 = (TextView) convertView.findViewById(R.id.device_lastData2);
//        data3 = (TextView) convertView.findViewById(R.id.device_lastData3);
//        data4 = (TextView) convertView.findViewById(R.id.device_lastData4);
//        for(i=1;i<=TOTAL;++i){
//            try {
//                Method fInit = this.getClass().getDeclaredMethod("setData" + String.valueOf(i), TextView.class);
//                if (i<=item.getData().size()) {
//                    TextView dataView = (TextView) convertView.findViewById(getContext().getResources().getIdentifier("device_lastData" + String.valueOf(i), "id", "com.norra.cloud"));
//                    fInit.invoke(this, dataView);
//                } else {
//                    fInit.invoke(this, null);
//                }
//            } catch (Exception e){
//                System.out.println(e);
//            }
//        }
        energy = (TextView) convertView.findViewById(R.id.device_lastEnergy);
        time = (TextView)convertView.findViewById(R.id.device_lastData_time);
        deviceIcon = (TextView) convertView.findViewById(R.id.device_icon);


        if(item.getAlert() == 1) {
            deviceNameView.setTextColor(HIGH); //设备标题警告
            deviceIcon.setTextColor(HIGH);
        } else {
            deviceNameView.setTextColor(NORMAL); //正常主题
            deviceIcon.setTextColor(NORMAL);
        }

        int i = 1;
        for(DataItem dItem : item.getData()){
            try {
                //Method fGet = this.getClass().getDeclaredMethod("getData" + String.valueOf(i));
                Method fSet = this.getClass().getDeclaredMethod("setData" + String.valueOf(i), TextView.class);
                //TextView dataText = (TextView)fGet.invoke(this);
                TextView dataText = (TextView) convertView.findViewById(rsrc.getIdentifier("device_lastData" + String.valueOf(i), "id", "com.norra.cloud"));
                if(dItem.getAlert() == 0)dataText.setTextColor(NORMAL);
                else if(dItem.getAlert() == 1)dataText.setTextColor(HIGH);
                else dataText.setTextColor(LOW);
                dataText.setText(dItem.getName()+":"+dItem.getValue()+dItem.getUnit()+" ");
                fSet.invoke(this, dataText);
                ++i;
            } catch (Exception e){
                System.out.println(e);
            }
        }
        for(;i<=TOTAL;++i){
            try {
                TextView dataText = (TextView) convertView.findViewById(rsrc.getIdentifier("device_lastData" + String.valueOf(i), "id", "com.norra.cloud"));
                Method fSet = this.getClass().getDeclaredMethod("setData" + String.valueOf(i), TextView.class);
                dataText.setText("");
                fSet.invoke(this, dataText);
            } catch (Exception e){
                System.out.println(e);
            }
        }

        String deviceName = item.getName();
        if (TextUtils.isEmpty(deviceName) || deviceName.trim().equals("")) {
            deviceName =  item.getIdentifier()+"";
        } else {
            deviceName = item.getIdentifier() + " " + item.getName();
        }
        deviceNameView.setText(deviceName);
        deviceIcon.setText(deviceName.substring(0, 1));
        if(item.geteAlert() == 1)energy.setTextColor(LOW);
        else energy.setTextColor(NORMAL);
        energy.setText("电量:"+item.getEnergy() + "%");
        time.setText(sdf.format(new Date(item.getTime()*1000)));
        return convertView;
    }

    public void setData1(TextView data1) {
        this.data1 = data1;
    }

    public void setData2(TextView data2) {
        this.data2 = data2;
    }

    public void setData3(TextView data3) {
        this.data3 = data3;
    }

    public void setData4(TextView data4) {
        this.data4 = data4;
    }

    public TextView getData1() {
        return data1;
    }

    public TextView getData2() {
        return data2;
    }

    public TextView getData3() {
        return data3;
    }

    public TextView getData4() {
        return data4;
    }
}
