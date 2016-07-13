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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by B402 on 2016/7/12.
 */
public class MoviesFragment extends Fragment {

    private ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    private static final String BASE_URL = "http://119.29.144.22:8001/api/movie";
    private ArrayList<Bitmap> BMImages = new ArrayList<Bitmap>();
    private JSONArray jsonArray = null;
    private ListView listView;

    private Handler handler_movies = new Handler();
    private Runnable runnable_movies = null;

    private String[] movie_titles;
    private String[] movie_scores;
    private String[] movie_types;
    private String[] movie_time_languages;
    private String[] movie_on_times;
    private String[] movie_actors;
    private String[] movie_descriptions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View movies_view = inflater.inflate(R.layout.fragment_movies, container, false);
        listView = (ListView)movies_view.findViewById(R.id.movies_listview);
        setDataList();

        runnable_movies = new Runnable() {
            @Override
            public void run() {
                if (dataList.size() != 0) {
                    MySimpleAdapter mySimpleAdapter = new MySimpleAdapter(getContext(), dataList, R.layout.movies_item_form,
                            new String[] {"ItemImage", "ItemTitle", "ItemScore"},
                            new int[] {R.id.movie_image, R.id.movie_title, R.id.movie_score_number});
                    mySimpleAdapter.setBMImages(BMImages);
                    listView.setAdapter(mySimpleAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), MovieDetailActivity.class);
                            intent.putExtra("ItemTitle", movie_titles[position]);
                            intent.putExtra("ItemScore", movie_scores[position]);
                            intent.putExtra("ItemType", movie_types[position]);
                            intent.putExtra("ItemTimeLanguage", movie_time_languages[position]);
                            intent.putExtra("ItemOnTime", movie_on_times[position]);
                            intent.putExtra("ItemActors", movie_actors[position]);
                            intent.putExtra("ItemDescription", movie_descriptions[position]);
                            intent.putExtra("ItemIndex", position);
                            Bitmap bitmap = BMImages.get(position);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                            byte[] data = bos.toByteArray();
                            intent.putExtra("ItemImage", data);
                            startActivity(intent);
                        }
                    });
                }
//                handler_movies.postDelayed(this, 60 * 1000);
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jsonArray = JsonParse.getJsonArray(BASE_URL);
                    dataList.clear();
                    BMImages.clear();
                    movie_actors = new String[jsonArray.length()];
                    movie_descriptions = new String[jsonArray.length()];
                    movie_on_times = new String[jsonArray.length()];
                    movie_scores = new String[jsonArray.length()];
                    movie_time_languages = new String[jsonArray.length()];
                    movie_titles = new String[jsonArray.length()];
                    movie_types = new String[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        movie_types[i] = object.getString("type");
                        movie_titles[i] = object.getString("name");
                        movie_time_languages[i] = object.getString("timeAndLanguage");
                        movie_scores[i] = object.getString("score");
                        movie_actors[i] = object.getString("actors");
                        movie_descriptions[i] = object.getString("description");
                        movie_on_times[i] = object.getString("onTime");
                        String imgUrl = object.getString("img");
                        Bitmap bitmap = ImageService.getBitmapFromUrl(imgUrl);
                        BMImages.add(bitmap);

                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("ItemImage", R.drawable.ic_photo_black_100dp);
                        map.put("ItemTitle", object.getString("name"));
                        map.put("ItemScore", object.getString("score"));
                        dataList.add(map);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void refreshMovies() {
        handler_movies.removeCallbacks(runnable_movies);
        handler_movies.post(runnable_movies);
    }
}
