package com.sysu.edgar.bach;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by B402 on 2016/7/12.
 */
public class MoviesFragment extends Fragment {

    private ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    private static final String BASE_URL = "http://119.29.144.22:8001/api/movie";
    private ListView listView;

    private Handler handler_movies = new Handler();
    private Runnable runnable_movies = null;

    public MovieDatabase movieDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View movies_view = inflater.inflate(R.layout.fragment_movies, container, false);
        listView = (ListView)movies_view.findViewById(R.id.movies_listview);
        setDataList();
        runnable_movies = new Runnable() {
            @Override
            public void run() {
                if (dataList.size() == movieDatabase.length && movieDatabase.length != 0) {
                    MySimpleAdapter mySimpleAdapter = new MySimpleAdapter(getContext(), dataList, R.layout.movies_item_form,
                            new String[] {"ItemImage", "ItemTitle", "ItemScore"},
                            new int[] {R.id.movie_image, R.id.movie_title, R.id.movie_score_number});
                    mySimpleAdapter.setBMImages(movieDatabase.images);
                    mySimpleAdapter.setMoviesFragment(MoviesFragment.this);
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
                    handler_movies.post(this);
                    dataList.clear();
                    for (int i = 0; i < movieDatabase.length; i++) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("ItemImage", R.drawable.ic_photo_black_100dp);
                        map.put("ItemTitle", movieDatabase.titles[i]);
                        map.put("ItemScore", movieDatabase.scores[i]);
                        dataList.add(map);
                    }
                    handler_movies.post(this);
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
}
