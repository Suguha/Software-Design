package com.sysu.edgar.beethoven.MovieFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sysu.edgar.beethoven.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Edgar on 2016/7/6.
 */
public class Coming_movies_fragment extends Fragment {

    private ArrayList<HashMap<String, Object>> dataArrayList = new ArrayList<HashMap<String, Object>>();
//    private LinearLayout linearLayout = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.coming_movies_layout, container, false);

        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.test_layout);
        //完成下面的getScrollData函数，获取数据，包括电影名称，电影海报图片，评分；
        getScrollData(getContext(), inflater, linearLayout);

        ListView scrollListView = (ListView)view.findViewById(R.id.coming_movies_listview);
        //完成下面的getData函数，获取数据，包括电影名称，电影海报图片，评分；
        getData();

        MySimpleAdapter_1 simpleAdapter = new MySimpleAdapter_1(getActivity(), dataArrayList, R.layout.coming_movies_listview_layout,
                new String[] {"ItemImage", "ItemTitle", "ItemNum", "ItemText", "ItemDate"}, new int[] {R.id.movie_image,
                R.id.movie_title, R.id.movie_wtw_number, R.id.movie_wtw_text, R.id.coming_movies_time_stamp});

        scrollListView.setAdapter(simpleAdapter);

        scrollListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //to goto movie detail activity;
                String test = "hello " + position;
                System.out.println(test);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    //需要后台数据
    private void getData() {
        dataArrayList.clear();
        //30是测试用的数据，具体情况要根据后台数据改
        for (int i = 0; i < 30; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.drawable.ic_photo_black_100dp);
            map.put("ItemTitle", "Title_" + i);
            map.put("ItemNum", 1000);
            map.put("ItemText", "人想看");
            map.put("ItemDate", "7月7日");
            dataArrayList.add(map);
        }
    }

    private void getScrollData(Context context, LayoutInflater inflater, LinearLayout ll) {
        //30是测试用的数据，具体情况要根据后台数据改
        for (int i = 0; i < 30; i++) {
            View hh = new View(context);
            hh = inflater.inflate(R.layout.coming_movies_scrolllist_layout, null, false);
            TextView date = (TextView)hh.findViewById(R.id.coming_movie_date);
            TextView score = (TextView)hh.findViewById(R.id.coming_movie_score);
            TextView title = (TextView)hh.findViewById(R.id.coming_movie_title);
            ImageView image = (ImageView)hh.findViewById(R.id.coming_movie_image);

            //从后台获取数据放到这里
            date.setText("7月7日");
            score.setText(1000 + "人想看");
            title.setText("Title_" + i);
            image.setImageDrawable(getResources().getDrawable(R.drawable.ic_photo_black_100dp));
            hh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goto the movie detail activity here
                    System.out.println("Hello ");
                }
            });
            ll.addView(hh, i);
        }
    }
}
