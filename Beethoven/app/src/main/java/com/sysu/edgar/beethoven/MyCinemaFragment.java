package com.sysu.edgar.beethoven;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Edgar on 2016/7/8.
 */
public class MyCinemaFragment extends Fragment {

    private ListView listView = null;
    private ArrayList<HashMap<String, Object>> dataArrayList = new ArrayList<HashMap<String, Object>>();
    private SimpleAdapter simpleAdapter;
    private LinearLayout locationLayout = null;
    private LocationManager locationManager = null;
    private Context context = null;
    private Location mLocation = null;
    private String localityName, provinceName, countryName, subLocalityName, thoroughfareName;
    private String errorMsg = "无法获取位置信息，请打开GPS";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View cinema_fragment_view = inflater.inflate(R.layout.cinema_fragment, container, false);
        context = this.getContext();
        final Geocoder geo = new Geocoder(this.getContext(), Locale.getDefault());
        listView = (ListView) cinema_fragment_view.findViewById(R.id.cinema_listview);

        locationLayout = (LinearLayout)cinema_fragment_view.findViewById(R.id.cinema_location_display);
        final TextView locationText = (TextView)locationLayout.findViewById(R.id.location_text);

        locationManager = (LocationManager)context.getSystemService(context.LOCATION_SERVICE);
        mLocation = getLocation();

        if (mLocation != null) {
            display(geo, mLocation);
            locationText.setText(provinceName + "," + localityName + "," + subLocalityName + "," + thoroughfareName);
        } else {
            locationText.setText(errorMsg);
        }

        setData();

        simpleAdapter = new SimpleAdapter(getActivity(), dataArrayList, R.layout.cinema_item_form,
                new String[]{"ItemName", "ItemPrice", "ItemUnit", "ItemAddress", "ItemDistance"},
                new int[]{R.id.cinema_item_name, R.id.cinema_item_price, R.id.cinema_item_unit,
                        R.id.cinema_item_address, R.id.cinema_item_distance});

        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //to goto cinema detail activity;
                String test = "hello " + position;
                System.out.println(test);
            }
        });

        locationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationText.setText("获取位置信息中...");
                mLocation = getLocation();
                if (mLocation != null) {
                    display(geo, mLocation);
                    locationText.setText(provinceName + "," + localityName + "," + subLocalityName + "," + thoroughfareName);
                } else {
                    locationText.setText(errorMsg);
                }
            }
        });

        return cinema_fragment_view;
    }

    private void setData() {
        dataArrayList.clear();

        for (int i = 0; i < 30; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemName", "电影院名字");
            map.put("ItemPrice", 30);
            map.put("ItemUnit", "元起");
            map.put("ItemAddress", "更加详细的地址信息");
            map.put("ItemDistance", "距离" + "120m");
            dataArrayList.add(map);
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ItemName", "");
        map.put("ItemPrice", "");
        map.put("ItemUnit", "");
        map.put("ItemAddress", "");
        map.put("ItemDistance", "");
        dataArrayList.add(map);
    }

    private Location getLocation() {
        Location location = null;
        //下面这段如果编译器提示有错，请忽略掉它的提示，运行起来没问题的；
        if (locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else if (locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) != null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
//        else {
//            Toast.makeText(context, "No location services found", Toast.LENGTH_SHORT).show();
//        }
        return location;
    }

    private void display(Geocoder geo, Location location) {
        List<Address> addresses = null;
        try {
            addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.isEmpty()) {
            Log.i("location", "addressed is Empty");
        }
        else {
            if (addresses.size() > 0) {
                thoroughfareName = addresses.get(0).getThoroughfare();
                localityName = addresses.get(0).getLocality();
                provinceName = addresses.get(0).getAdminArea();
                countryName = addresses.get(0).getCountryName();
                subLocalityName = addresses.get(0).getSubLocality();
                Log.i("location", addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +
                        ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
            }
        }
    }

}
