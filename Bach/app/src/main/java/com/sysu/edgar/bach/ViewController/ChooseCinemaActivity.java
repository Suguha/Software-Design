package com.sysu.edgar.bach.ViewController;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sysu.edgar.bach.Cache.CinemaDatabase;
import com.sysu.edgar.bach.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Edgar on 2016/7/14.
 */
public class ChooseCinemaActivity extends AppCompatActivity {
    public CinemaDatabase cinemaDatabase = null;

    private ListView listView = null;
    private String requestURL = "http://119.29.144.22:8001/api/cinema?qu=";
    private String BASE_URL = "http://119.29.144.22:8001/api/cinema?movieId=";
    private String movieIdFromButton = "";
    private ArrayList<HashMap<String, Object>> dataArrayList = new ArrayList<HashMap<String, Object>>();
    private SimpleAdapter simpleAdapter;
    private LinearLayout locationLayout = null;
    private LocationManager locationManager = null;
    private Context context = null;
    private String localityName, provinceName, subLocalityName, thoroughfareName;
    private String errorMsg = "无法获取位置信息，请打开GPS";
    private String subLocality;
    Geocoder geo = null;
    private TextView locationText;

    private Handler handler_cinema = new Handler();
    private Runnable runnable_cinema = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.MyThemeStyle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_cinema);

        Bundle bundle = getIntent().getExtras();
        movieIdFromButton = bundle.getString("MovieId");

        listView = (ListView)this.findViewById(R.id.cinema_listview);
        geo = new Geocoder(this, Locale.CHINA);
        locationLayout = (LinearLayout)this.findViewById(R.id.cinema_location_display);
        locationText = (TextView)locationLayout.findViewById(R.id.location_text);
        locationManager = (LocationManager)this.getSystemService(context.LOCATION_SERVICE);
        setLocation();
        setData();

        ImageButton btn_back = (ImageButton)this.findViewById(R.id.about_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        locationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationText.setText("获取位置信息中...");
                listView.setSelection(0);
                listView.smoothScrollToPosition(0);
                setLocation();
                setData();
                refreshCinemaFragment();
            }
        });

        runnable_cinema = new Runnable() {
            @Override
            public void run() {
                try {
                    if (dataArrayList.size() == cinemaDatabase.length && cinemaDatabase.count == cinemaDatabase.length
                            && cinemaDatabase.length != 0) {
                        listView.setVisibility(View.VISIBLE);
                        simpleAdapter = new SimpleAdapter(ChooseCinemaActivity.this, dataArrayList, R.layout.cinema_item_form,
                                new String[]{"ItemName", "ItemAddress", "ItemTel"},
                                new int[]{R.id.cinema_item_name, R.id.cinema_item_address, R.id.cinema_item_telephone});
                        listView.setAdapter(simpleAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //to goto cinema detail activity;
                                Toast.makeText(ChooseCinemaActivity.this, "Not finished yet!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        listView.setVisibility(View.INVISIBLE);
                        dataArrayList.clear();
                        for (int i = 0; i < cinemaDatabase.count; i++) {
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("ItemName", cinemaDatabase.names[i]);
                            map.put("ItemAddress", cinemaDatabase.locations[i]);
                            map.put("ItemTel", cinemaDatabase.tels[i]);
                            dataArrayList.add(map);
                        }
                        handler_cinema.postDelayed(this, 1 * 1000);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    handler_cinema.postDelayed(this, 2 * 1000);
                } catch (IndexOutOfBoundsException e1) {
                    e1.printStackTrace();
                    handler_cinema.postDelayed(this, 2 * 1000);
                }
            }
        };
        handler_cinema.postDelayed(runnable_cinema, 3 * 1000);
    }

    @Override
    public void onDestroy() {
        handler_cinema.removeCallbacks(runnable_cinema);
        super.onDestroy();
    }

    private void setLocation() {
        Location location = null;
        //下面这段如果编译器提示有错，请忽略掉它的提示，运行起来没问题的；
        if (locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else if (locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) != null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (location != null) {
            displayLocation(geo, location);
            locationText.setText(provinceName + "," + localityName + "," + subLocalityName + "," + thoroughfareName);
            try {
//                subLocality = URLEncoder.encode(subLocalityName, "UTF-8");
                subLocality = URLEncoder.encode("白云", "UTF-8");
                requestURL = BASE_URL + movieIdFromButton + "&qu=" + subLocality;
                System.out.println("!!!!!!!!!!request url: " + requestURL);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            locationText.setText(errorMsg);
            try {
                subLocality = URLEncoder.encode("白云", "UTF-8");
                requestURL = BASE_URL + movieIdFromButton + "&qu=" + subLocality;
                System.out.println("!!!!!!!!!!request url: " + requestURL);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayLocation(Geocoder geo, Location location) {
        List<Address> addresses = null;
        try {
            addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            thoroughfareName = addresses.get(0).getThoroughfare();
            localityName = addresses.get(0).getLocality();
            provinceName = addresses.get(0).getAdminArea();
            subLocalityName = addresses.get(0).getSubLocality();
        }
    }

    private void setData() {
        cinemaDatabase = new CinemaDatabase(requestURL);
    }

    private void refreshCinemaFragment() {
        handler_cinema.removeCallbacks(runnable_cinema);
        handler_cinema.post(runnable_cinema);
    }
}
