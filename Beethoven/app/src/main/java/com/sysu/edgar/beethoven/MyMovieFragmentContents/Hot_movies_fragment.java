package com.sysu.edgar.beethoven.MyMovieFragmentContents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sysu.edgar.beethoven.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Edgar on 2016/7/6.
 */
public class Hot_movies_fragment extends Fragment {

    private ArrayList<HashMap<String, Object>> dataArrayList = new ArrayList<HashMap<String, Object>>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.hot_movies_childfragment, container, false);
        ListView listView = (ListView)view.findViewById(R.id.hot_movies_listview);

        //完成下面的getData函数，获取数据，包括电影名称，电影海报图片，评分；
        getData();

        MySimpleAdapter simpleAdapter = new MySimpleAdapter(getActivity(), dataArrayList, R.layout.movies_item_form,
                new String[] {"ItemImage", "ItemTitle", "ItemText", "ItemNum"}, new int[] {R.id.movie_image, R.id.movie_title,
                R.id.movie_score_text, R.id.movie_score_number});

        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    public void getData() {
        dataArrayList.clear();

        //30是测试用的数据，具体情况要根据后台数据改
        for (int i = 0; i < 30; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.drawable.ic_photo_black_100dp);
            map.put("ItemTitle", "Title_" + i);
            map.put("ItemText", "评分：");
            map.put("ItemNum", 9.6);
            dataArrayList.add(map);
        }
    }
}
