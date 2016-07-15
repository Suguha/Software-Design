package com.sysu.edgar.bach.ViewController.Fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.sysu.edgar.bach.Cache.CinemaDatabase;
import com.sysu.edgar.bach.Cache.MovieDatabase;
import com.sysu.edgar.bach.R;
import com.sysu.edgar.bach.ViewController.CinemaDetailActivity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Edgar on 2016/7/12.
 */
public class CinemaFragment extends Fragment {

    public MovieDatabase movieDatabase = new MovieDatabase();
    public CinemaDatabase cinemaDatabase = null;

    private ListView listView = null;
    private String requestURL = "http://119.29.144.22:8001/api/cinema?qu=";
    private final String BASE_URL = "http://119.29.144.22:8001/api/cinema?qu=";
    private ArrayList<HashMap<String, Object>> dataArrayList = new ArrayList<HashMap<String, Object>>();
    private ArrayList<HashMap<String, Object>> dataBuffer = new ArrayList<HashMap<String, Object>>();
    private SimpleAdapter simpleAdapter;
    private LinearLayout locationLayout = null;
    private LocationManager locationManager = null;
    private Context context = null;
    private String localityName, provinceName, subLocalityName, thoroughfareName;
    private String errorMsg = "无法获取位置信息，请打开GPS";
    private String subLocality;
    Geocoder geo = null;
    private TextView locationText;
    private TextView loading_text;

    private Handler handler_cinema = new Handler();
    private Runnable runnable_cinema = null;
    private Handler handler_download = new Handler();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View cinema_view = inflater.inflate(R.layout.fragment_cinemas, container, false);
        loading_text = (TextView)cinema_view.findViewById(R.id.loading_cinemas_text);
        loading_text.setVisibility(View.VISIBLE);
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
                try {
                    if (dataArrayList.size() == cinemaDatabase.length && movieDatabase.length != 0
                            && cinemaDatabase.length != 0 && cinemaDatabase.count == cinemaDatabase.length) {
                        loading_text.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
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
                                intent.putExtra("CinemaName", cinemaDatabase.names[position]);
                                intent.putExtra("CinemaLocation", cinemaDatabase.locations[position]);
                                intent.putExtra("CinemaMovies", cinemaDatabase.movies[position]);
                                intent.putExtra("CinemaTransport", cinemaDatabase.transports[position]);
                                intent.putExtra("CinemaUrl", cinemaDatabase.urls[position]);
                                intent.putExtra("CinemaId", cinemaDatabase.cinemaIds[position]);
                                intent.putExtra("CinemaTel", cinemaDatabase.tels[position]);
                                intent.putExtra("Index", position);

                                intent.putExtra("DatabaseTitles", movieDatabase.titles);
                                intent.putExtra("DatabaseActors", movieDatabase.actors);
                                intent.putExtra("DatabaseTimeLanguages", movieDatabase.time_languages);
                                intent.putExtra("DatabaseScores", movieDatabase.scores);
                                intent.putExtra("DatabaseTypes", movieDatabase.types);
                                intent.putExtra("DatabaseOnTimes", movieDatabase.on_times);
                                intent.putExtra("DatabaseDescriptions", movieDatabase.descriptions);
                                intent.putExtra("DatabaseIds", movieDatabase.ids);
                                intent.putExtra("DatabaseImgUrls", movieDatabase.imgUrls);
                                startActivity(intent);
                            }
                        });
                    } else {
                        loading_text.setVisibility(View.VISIBLE);
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
                    handler_cinema.postDelayed(this, 3 * 1000);
                } catch (IndexOutOfBoundsException e1) {
                    e1.printStackTrace();
                    handler_cinema.postDelayed(this, 3 * 1000);
                }
            }
        };
        handler_cinema.postDelayed(runnable_cinema, 3 * 1000);

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
        cinemaDatabase = new CinemaDatabase(requestURL);
    }

    private void refreshCinemaFragment() {
        handler_cinema.removeCallbacks(runnable_cinema);
        handler_cinema.post(runnable_cinema);
    }

    public void setMovieDataBase(MovieDatabase other) {
        movieDatabase = new MovieDatabase(other);
    }
}
