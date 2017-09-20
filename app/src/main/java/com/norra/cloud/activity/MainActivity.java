package com.norra.cloud.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.norra.cloud.R;
import com.norra.cloud.api.API;
import com.norra.cloud.api.response.DeviceGroupResponse;
import com.norra.cloud.api.response.DeviceGroupResponse.DeviceGroup;
import com.norra.cloud.entity.DeviceItem;
import com.norra.cloud.http.frame.HError;
import com.norra.cloud.http.frame.ResponseListener;
import com.norra.cloud.widget.EasySlidingTabs;
import com.norra.cloud.widget.SimpleDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liurenhui on 15/1/22.
 * Modified by shenletao after 16/7/1
 */
public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private PopupWindow menuWindow;
    private ImageView refreshButton;
    private ImageView moreButton;
    private ViewPager viewPager;
    private EasySlidingTabs slidingTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moreButton = (ImageView) findViewById(R.id.more_btn);
        refreshButton = (ImageView) findViewById(R.id.refresh_btn);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        slidingTabs = (EasySlidingTabs) findViewById(R.id.easy_sliding_tabs);
        refreshButton.setOnClickListener(this);
        moreButton.setOnClickListener(this);
//        AlertDialog.Builder dialog  = new AlertDialog.Builder(MainActivity.this);
//        dialog.setMessage("message");
//        dialog.show();
//        loadDeviceType();


        loadGroupDate();

    }

//    private void loadDeviceType(){
//        API.getDeviceType(this, new ResponseListener<DeviceTypeResponse>() {
//            @Override
//            public void onSuccess(DeviceTypeResponse response) {
//                onLoadDeviceTypeSuccess(response);
//            }
//
//            @Override
//            public void onError(HError error) {
//                if (error.isTokenExpiredError()) {
//                    onTokenExpired();
//                } else {
//                    super.onError(error);
//                }
//            }
//        });
//    }

    private void loadGroupDate() {
        API.getDeviceList(this, new ResponseListener<DeviceGroupResponse>() {
            @Override
            public void onSuccess(DeviceGroupResponse response) {
                onLoadDeviceSuccess(response);
            }

            @Override
            public void onError(HError error) {
                if (error.isTokenExpiredError()) {
                    onTokenExpired();
                } else {
                    super.onError(error);
                }
            }
        });
    }

    private void onLoadDeviceSuccess(DeviceGroupResponse response) {

        ArrayList<String> title = new ArrayList<>();
        ArrayList<ArrayList<DeviceItem>> deviceList = new ArrayList<>();
        if (response.ungroup != null && response.ungroup.size() > 0) {
            deviceList.add(response.ungroup);
            title.add(getString(R.string.un_group));
        }

        if (response.shared != null && response.shared.size() > 0) {
            deviceList.add(response.shared);
            title.add(getString(R.string.shared_group));
        }
        for (DeviceGroup deviceGroup : response.groups) {
            title.add(deviceGroup.name);
            if (deviceGroup.devices == null) {
                deviceList.add(new ArrayList<DeviceItem>());
            } else {
                deviceList.add(deviceGroup.devices);
            }
        }

        List<Fragment> fragmentList = new ArrayList<>();
        for (ArrayList<DeviceItem> list : deviceList) {
            DeviceListFragment fragment = new DeviceListFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("device_list", list);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }

        TabsFragmentAdapter adapter = new TabsFragmentAdapter(getSupportFragmentManager(), title, fragmentList);
        viewPager.setAdapter(adapter);
        //viewPager.setOffscreenPageLimit(adapter.getCount()-1);
        //adapter.notifyDataSetChanged(); //更新显示
        slidingTabs.setViewPager(viewPager);
    }

//    private void onLoadDeviceTypeSuccess(DeviceTypeResponse response){
//        List<DeviceType> deviceTypes = response.getDeviceTypes();
//        CloudApp cloudApp = (CloudApp)getApplication();
//        Map<Integer, DeviceType> devTypeMap = new HashMap<>();
//        for(DeviceType dt : deviceTypes){
//            devTypeMap.put(dt.getTypeId(), dt);
//        }
//        cloudApp.setDevTypeMap(devTypeMap);
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object obj = parent.getAdapter().getItem(position);
        if (obj != null) {
            DeviceItem item = (DeviceItem) obj;
            Intent intent = new Intent(MainActivity.this, DeviceDetailActivity.class);
            intent.putExtra("device_item", item);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == moreButton.getId()) {
            if (menuWindow == null) {
                View menuView = getLayoutInflater().inflate(R.layout.more_menu, null);
                menuView.findViewById(R.id.about).setOnClickListener(this);
                menuView.findViewById(R.id.logout).setOnClickListener(this);
                menuWindow = new PopupWindow(menuView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                menuWindow.setOutsideTouchable(true);
                menuWindow.setBackgroundDrawable(new BitmapDrawable());
                menuWindow.setTouchable(true);
                menuWindow.setFocusable(true);
            }
            menuWindow.showAsDropDown(moreButton);
        } else if (v.getId() == R.id.refresh_btn){
            Intent intent = new Intent(this, this.getClass());
            startActivity(intent);
        } else if (v.getId() == R.id.about) {
            menuWindow.dismiss();
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.logout) {
            menuWindow.dismiss();
            SimpleDialog.show(this, getString(R.string.remind), getString(R.string.confirm_log_out), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logout();
                }
            });
        }
    }

    public void logout() {
        String appToken = mUser.getAppToken();
        mUser.logout();
        API.logout(null, appToken, null);//退出登录
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
