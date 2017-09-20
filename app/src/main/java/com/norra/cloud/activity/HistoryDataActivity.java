package com.norra.cloud.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.norra.cloud.R;
import com.norra.cloud.adapter.ListAdapter;
import com.norra.cloud.api.API;
import com.norra.cloud.api.response.HistoryDataResponse;
import com.norra.cloud.http.frame.HError;
import com.norra.cloud.http.frame.ResponseListener;
import com.norra.cloud.utils.TimeUtil;
import com.norra.cloud.utils.Toaster;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ics.datepicker.DateTimePickerDialog;

/**
 * Created by liurenhui on 15/1/26.
 * Modified by shenletao after 2017-01-17
 */
public class HistoryDataActivity extends TitleBarActivity implements View.OnClickListener {
    private Resources rsrc;
    private TextView fromTimeView;
    private TextView endTimeView;
    private Button okBtn;
    private ListView listView;
    private View dataListHeader;
    private View emptyView;
    private TextView footerLoadMoreView;
    private TextView totalCountView;
    private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd\nHH:mm:ss");
    private DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private DataAdapter dataAdapter;

    private DateTimePickerDialog dialog;
    private String deviceId;
    private String fromTime;
    private String endTime;
    private int defaultPageSize = 30;
    private int currentPage = 0;
    int flag = 1;
    TextView title1; //= (TextView)findViewById(R.id.his_head_item1);
    TextView title2;
    TextView title3;
    TextView title4;
    TextView titleTime;
    private int dataSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rsrc = getResources();
        deviceId = getIntent().getStringExtra("device_id");
        setContentView(R.layout.activity_history_data);

        setTitle(R.string.data_search);
        fromTimeView = (TextView) findViewById(R.id.from_time);
        endTimeView = (TextView) findViewById(R.id.end_time);
        okBtn = (Button) findViewById(R.id.search_btn);
        emptyView = findViewById(R.id.empty_view);
        listView = (ListView) findViewById(R.id.data_list_view);
        dataListHeader = findViewById(R.id.data_list_header_layout);
        dataListHeader.setVisibility(View.INVISIBLE);


        titleTime = (TextView)findViewById(R.id.his_head_time);
        titleTime.setText(R.string.time);
        totalCountView = (TextView) findViewById(R.id.total_count);

        fromTimeView.setOnClickListener(this);
        endTimeView.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();
        String defaultEndTime = TimeUtil.format(calendar.getTime(), TimeUtil.yyyy_MM_dd_HH_mm);
        endTimeView.setText(defaultEndTime);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String defaultFromTime = TimeUtil.format(calendar.getTime(), TimeUtil.yyyy_MM_dd_HH_mm);
        fromTimeView.setText(defaultFromTime);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == fromTimeView.getId()) {
            flag = 1;
            showSelectTimeDialog();
        } else if (v.getId() == endTimeView.getId()) {
            flag = 2;
            showSelectTimeDialog();
        } else if (v.getId() == okBtn.getId()) {
            try {
                getDeviceDataByTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (v.getId() == footerLoadMoreView.getId()) {
            getData(currentPage, defaultPageSize);
        }
    }

    private void showSelectTimeDialog() {
        if (dialog == null) {
            dialog = new DateTimePickerDialog(this);
            dialog.setDatePickListener(new DateTimePickerDialog.DatePickerListener() {
                @Override
                public void onPickDate(Calendar calendar) {
                    String time = TimeUtil.format(calendar.getTime(), TimeUtil.yyyy_MM_dd_HH_mm);
                    if (flag == 1) {
                        fromTimeView.setText(time);
                    } else if (flag == 2) {
                        endTimeView.setText(time);
                    }
                }
            });
        }
        dialog.show();
    }

    private void getDeviceDataByTime() throws ParseException {
        fromTime = String.valueOf(sdf2.parse(fromTimeView.getText().toString()).getTime());
        endTime = String.valueOf(sdf2.parse(endTimeView.getText().toString()).getTime());
        if (TextUtils.isEmpty(fromTime) || TextUtils.isEmpty(endTime)) {
            Toaster.shortToast(R.string.select_time_search_device_data);
            return;
        }

        currentPage = 0;
        getData(currentPage, defaultPageSize);

    }

    private void getData(final int page, int pageSize) {
        API.getDeviceDataByTime(this, page, pageSize, deviceId, fromTime, endTime, new ResponseListener<HistoryDataResponse>() {
            @Override
            public void onSuccess(HistoryDataResponse response) {
                totalCountView.setText(response.getTotal() + " " + getString(R.string.result_num));
                totalCountView.setVisibility(View.VISIBLE);
                if (response.getData() != null && response.getData().size() > 0) {
                    setTitles(response.getTitle());
                    emptyView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    dataListHeader.setVisibility(View.VISIBLE);
                    if (listView.getFooterViewsCount() == 0) {
                        View footer = getLayoutInflater().inflate(R.layout.data_list_footer, null);
                        footerLoadMoreView = (TextView) footer.findViewById(R.id.load_more);
                        footerLoadMoreView.setOnClickListener(HistoryDataActivity.this);
                        listView.addFooterView(footer);
                    }
                } else {
                    dataListHeader.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    return;
                }
                if (response.isHasNext()) {
                    currentPage++;
                    footerLoadMoreView.setClickable(true);
                    footerLoadMoreView.setText(R.string.load_more);
                } else {
                    if (footerLoadMoreView != null) {
                        footerLoadMoreView.setText(R.string.load_finish);
                        footerLoadMoreView.setClickable(false);
                    }
                }
                if (dataAdapter == null) {
                    dataAdapter = new DataAdapter(HistoryDataActivity.this);
                    dataAdapter.setList(response.getData());
                    listView.setAdapter(dataAdapter);
                } else {
                    if (page == 0) {
                        dataAdapter.getList().clear();

                    }
                    dataAdapter.getList().addAll(response.getData());
                    dataAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(HError error) {
                if (error.getErrorCode() == 100) {
                    onTokenExpired();
                } else {
                    emptyView.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    class DataAdapter extends ListAdapter<Map<String, Object>> {

        public DataAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = inflaterView(R.layout.data_list_item);
                holder = new ViewHolder();
                try {
                    for (int i=1; i<=dataSize; ++i) {
                        Method setTitle = holder.getClass().getDeclaredMethod("setData" + String.valueOf(i), TextView.class);

                        TextView dataView = (TextView)convertView.findViewById(rsrc.getIdentifier("his_data_item"+String.valueOf(i), "id", "com.norra.cloud"));
                        setTitle.invoke(holder, dataView);

                        System.out.println(dataView == null);
                    }

                } catch (Exception e){}
                holder.time = (TextView) convertView.findViewById(R.id.time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Map<String, Object> item = getItem(position);
            if (item.size() > 0) {
                try {
                    for (int i = 1; i<item.size(); ++i) { // exclude time data
                        Method getData = holder.getClass().getDeclaredMethod("getData" + String.valueOf(i));
                        Method setData = holder.getClass().getDeclaredMethod("setData" + String.valueOf(i), TextView.class);
                        TextView dataView = (TextView)getData.invoke(holder);
                        Double d = (Double)item.get("data"+String.valueOf(i));
                        //System.out.print((dataView==null?"true":"false")+" "+d+" ");
                        dataView.setText((d).floatValue()+"");
                        setData.invoke(holder, dataView);
                    }
                } catch (Exception e){System.out.println(e);}

                //System.out.println(sdf.format(new Date(((Double)item.get("time")).longValue())));
                holder.time.setText(sdf.format(new Date(((Double)item.get("time")).longValue())));
            }
            return convertView;
        }
    }

    static class ViewHolder {
        TextView data1;
        TextView data2;
        TextView data3;
        TextView data4;
        TextView time;


        public TextView getData1() {
            return data1;
        }

        public void setData1(TextView data1) {
            this.data1 = data1;
        }

        public TextView getData2() {
            return data2;
        }

        public void setData2(TextView data2) {
            this.data2 = data2;
        }

        public TextView getData3() {
            return data3;
        }

        public void setData3(TextView data3) {
            this.data3 = data3;
        }

        public TextView getData4() {
            return data4;
        }

        public void setData4(TextView data4) {
            this.data4 = data4;
        }

        public TextView getTime() {
            return time;
        }

        public void setTime(TextView time) {
            this.time = time;
        }


    }

    public TextView getTitle1() {
        return title1;
    }

    public void setTitle1(TextView title1) {
        this.title1 = title1;
    }

    public TextView getTitle2() {
        return title2;
    }

    public void setTitle2(TextView title2) {
        this.title2 = title2;
    }

    public TextView getTitle3() {
        return title3;
    }

    public void setTitle3(TextView title3) {
        this.title3 = title3;
    }

    public TextView getTitle4() {
        return title4;
    }

    public void setTitle4(TextView title4) {
        this.title4 = title4;
    }

    public static int getCompentID(String packageName, String className, String idName) {
        int id = 0;
        try {
            Class<?> cls = Class.forName(packageName + ".R$" + className);
            id = cls.getField(idName).getInt(cls);
        } catch (Exception e) {
            Log.e("Error", "缺少" + idName + "文件!");
            e.printStackTrace();
        }
        return id;
    }

    public void setTitles(List<String> list){
        try {
            int i = 1;
            for (String title : list) {
                Method setTitle = this.getClass().getDeclaredMethod("setTitle" + String.valueOf(i), TextView.class);
                TextView titleView = (TextView)findViewById(rsrc.getIdentifier("his_head_item"+String.valueOf(i), "id", "com.norra.cloud"));
                titleView.setText(title);
                setTitle.invoke(this, titleView);
                ++i;
            }
            dataSize = list.size();
        } catch (Exception e){}
    }
}
