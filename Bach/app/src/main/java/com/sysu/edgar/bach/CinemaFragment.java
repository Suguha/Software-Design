package com.sysu.edgar.bach;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by B402 on 2016/7/12.
 */
public class CinemaFragment extends Fragment {

    private ListView listView = null;
    private String requestURL = "http://119.29.144.22:8001/api/cinema?qu=";
    private final String BASE_URL ="http://119.29.144.22:8001/api/cinema?qu=";
    private ArrayList<HashMap<String, Object>> dataArrayList = new ArrayList<HashMap<String, Object>>();
    private SimpleAdapter simpleAdapter;
    private LinearLayout locationLayout = null;
    private LocationManager locationManager = null;
    private Context context = null;
    private String localityName, provinceName, subLocalityName, thoroughfareName;
    private String errorMsg = "无法获取位置信息，请打开GPS";
    private JSONArray jsonArray;
    private String subLocality;
    Geocoder geo = null;
    private TextView locationText;
    private String[] cinema_names;
    private String[] cinema_locations;
    private String[] cinema_urls;
    private String[] cinema_movies;
    private String[] cinema_transports;
    private String[] cinema_tels;
    private String[] cinema_ids;

    private Handler handler_cinema = new Handler();
    private Runnable runnable_cinema = null;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View cinema_view = inflater.inflate(R.layout.fragment_cinemas, container, false);
        context = getContext();
        listView = (ListView) cinema_view.findViewById(R.id.cinema_listview);
        geo = new Geocoder(getContext(), Locale.CHINA);
        locationLayout = (LinearLayout)cinema_view.findViewById(R.id.cinema_location_display);
        locationText = (TextView)locationLayout.findViewById(R.id.location_text);
        locationManager = (LocationManager)context.getSystemService(context.LOCATION_SERVICE);
        setLocation();
        setData();

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
                if (dataArrayList.size() != 0) {
                    simpleAdapter = new SimpleAdapter(getActivity(), dataArrayList, R.layout.cinema_item_form,
                            new String[]{"ItemName", "ItemAddress", "ItemTel"},
                            new int[]{R.id.cinema_item_name, R.id.cinema_item_address, R.id.cinema_item_telephone});
                    listView.setAdapter(simpleAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //to goto cinema detail activity;
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), CinemaDetailActivity.class);
                            intent.putExtra("CinemaName", cinema_names[position]);
                            intent.putExtra("CinemaLocation", cinema_locations[position]);
                            intent.putExtra("CinemaMovies", cinema_movies[position]);
                            intent.putExtra("CinemaTransport", cinema_transports[position]);
                            intent.putExtra("CinemaUrl", cinema_urls[position]);
                            intent.putExtra("CinemaId", cinema_ids[position]);
                            intent.putExtra("CinemaTel", cinema_tels[position]);
                            intent.putExtra("Index", position);
                            startActivity(intent);
                        }
                    });
                }
//                handler_cinema.postDelayed(this, 60 * 1000);
            }
        };
        handler_cinema.postDelayed(runnable_cinema, 5 * 1000);

        return cinema_view;
    }

    @Override
    public void onDestroy() {
        System.out.println("Destroy Cinema Fragment!");
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
                subLocality = URLEncoder.encode(subLocalityName, "UTF-8");
                requestURL = BASE_URL + subLocality;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            locationText.setText(errorMsg);
            requestURL = BASE_URL;
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jsonArray = JsonParse.getJsonArray(requestURL);

                    cinema_names = new String[jsonArray.length()];
                    cinema_ids = new String[jsonArray.length()];
                    cinema_locations = new String[jsonArray.length()];
                    cinema_movies = new String[jsonArray.length()];
                    cinema_tels = new String[jsonArray.length()];
                    cinema_transports = new String[jsonArray.length()];
                    cinema_urls = new String[jsonArray.length()];
                    dataArrayList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        cinema_locations[i] = object.getString("location");
                        cinema_urls[i] = object.getString("url");
                        cinema_ids[i] = object.getString("cinemaId");
                        cinema_tels[i] = object.getString("tel");
                        cinema_movies[i] = object.getString("movies");
                        cinema_names[i] = object.getString("name");
                        cinema_transports[i] = object.getString("transport");

                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("ItemName", object.getString("name"));
                        map.put("ItemAddress", object.getString("location"));
                        map.put("ItemTel", object.getString("tel"));
                        dataArrayList.add(map);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void refreshCinemaFragment() {
        handler_cinema.removeCallbacks(runnable_cinema);
        handler_cinema.post(runnable_cinema);
    }
}
