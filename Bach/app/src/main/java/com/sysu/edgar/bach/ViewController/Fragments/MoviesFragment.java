package com.sysu.edgar.bach.ViewController.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sysu.edgar.bach.Adapter.MySimpleAdapter;
import com.sysu.edgar.bach.Cache.MovieDatabase;
import com.sysu.edgar.bach.R;
import com.sysu.edgar.bach.ViewController.ChooseCinemaActivity;
import com.sysu.edgar.bach.ViewController.MovieDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Edgar on 2016/7/12.
 */
public class MoviesFragment extends Fragment {

    private ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    private static final String BASE_URL = "http://119.29.144.22:8001/api/movie";
    private final static String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/Download/Bach/";
    private ListView listView;
    private TextView loading_text;

    private Handler handler_movies = new Handler();
    private Runnable runnable_movies = null;

    private MySimpleAdapter mySimpleAdapter;
    private Bitmap[] BMImages;
    public MovieDatabase movieDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View movies_view = inflater.inflate(R.layout.fragment_movies, container, false);
        loading_text = (TextView)movies_view.findViewById(R.id.loading_movies_text);
        loading_text.setVisibility(View.VISIBLE);
        listView = (ListView)movies_view.findViewById(R.id.movies_listview);
        setDataList();
        runnable_movies = new Runnable() {
            @Override
            public void run() {
                if (dataList.size() == movieDatabase.length && movieDatabase.count_imgs == movieDatabase.length
                        && movieDatabase.count == movieDatabase.length && movieDatabase.length != 0) {
                    setBMImages();
                    loading_text.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    mySimpleAdapter = new MySimpleAdapter(getContext(), dataList, R.layout.movies_item_form,
                            new String[] {"ItemImage", "ItemTitle", "ItemScore"},
                            new int[] {R.id.movie_image, R.id.movie_title, R.id.movie_score_number});
                    mySimpleAdapter.setMoviesFragment(MoviesFragment.this);
                    mySimpleAdapter.setImages(BMImages);
                    listView.setAdapter(mySimpleAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), MovieDetailActivity.class);
                            intent.putExtra("ItemTitle", movieDatabase.titles[position]);
                            intent.putExtra("ItemScore", movieDatabase.scores[position]);
                            intent.putExtra("ItemType", movieDatabase.types[position]);
                            intent.putExtra("ItemTimeLanguage", movieDatabase.time_languages[position]);
                            intent.putExtra("ItemOnTime", movieDatabase.on_times[position]);
                            intent.putExtra("ItemActors", movieDatabase.actors[position]);
                            intent.putExtra("ItemDescription", movieDatabase.descriptions[position]);
                            intent.putExtra("ItemIndex", position);
                            intent.putExtra("ItemId", movieDatabase.ids[position]);
                            intent.putExtra("ItemUrl", movieDatabase.imgUrls[position]);
                            startActivity(intent);
                        }
                    });
                } else {
                    System.out.println("!!!!!!!!!Count:" + movieDatabase.count + " Count Imgs: " + movieDatabase.count_imgs);
                    listView.setVisibility(View.INVISIBLE);
                    loading_text.setVisibility(View.VISIBLE);
                    dataList.clear();
                    for (int i = 0; i < movieDatabase.count; i++) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("ItemImage", R.drawable.ic_photo_black_100dp);
                        map.put("ItemTitle", movieDatabase.titles[i]);
                        map.put("ItemScore", movieDatabase.scores[i]);
                        dataList.add(map);
                    }
                    handler_movies.postDelayed(this, 1 * 1000);
                }
            }
        };
        handler_movies.postDelayed(runnable_movies, 3 * 1000);

        return movies_view;
    }

    @Override
    public void onDestroy() {
        System.out.println("Destroy Movie Fragment!");
        handler_movies.removeCallbacks(runnable_movies);
        super.onDestroy();
    }

    private void setDataList() {
        movieDatabase = new MovieDatabase(BASE_URL);

    }

    public void refreshMovies() {
        handler_movies.removeCallbacks(runnable_movies);
        handler_movies.post(runnable_movies);
    }

    public void chooseCinema(int position) {
        Intent intent = new Intent();
        intent.setClass(getContext(), ChooseCinemaActivity.class);
        intent.putExtra("MovieId", movieDatabase.ids[position]);
        startActivity(intent);
    }

    public void setBMImages() {
        try {
            BMImages = new Bitmap[movieDatabase.length];
            for (int i = 0; i < movieDatabase.length; i++) {
                System.out.println("Creating image " + i);
                Bitmap bitmap = BitmapFactory.decodeFile(ALBUM_PATH + movieDatabase.ids[i] + ".png");
                BMImages[i] = bitmap;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
