package com.norra.cloud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.norra.cloud.R;
import com.norra.cloud.entity.DeviceItem;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/19.
 */
public class DeviceListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ArrayList<DeviceItem> deviceList;

    public DeviceListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceList = (ArrayList<DeviceItem>) getArguments().getSerializable("device_list");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.device_list, null);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        DeviceAdapter adapter = new DeviceAdapter(getActivity());
        adapter.setList(deviceList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object obj = parent.getAdapter().getItem(position);
        if (obj != null) {
            DeviceItem item = (DeviceItem) obj;

            Intent intent = new Intent(getActivity(), DeviceDetailActivity.class);
            intent.putExtra("deviceId", item.getDeviceId());
            startActivity(intent);
        }
    }


}
