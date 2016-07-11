package com.sysu.edgar.beethoven.MyAccountFragmentContents;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.sysu.edgar.beethoven.MovieDetailActivity;
import com.sysu.edgar.beethoven.MyMovieFragmentContents.MySimpleAdapter_1;
import com.sysu.edgar.beethoven.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Edgar on 2016/7/11.
 */
public class WantedActivity extends AppCompatActivity {

    private ArrayList<HashMap<String, Object>> dataArrayList = new ArrayList<HashMap<String, Object>>();
    private ListView listView = null;
    private MySimpleAdapter_1 simpleAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.MyThemeStyle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wanted);

        listView = (ListView)this.findViewById(R.id.wanted_listview);
        setData();
        simpleAdapter = new MySimpleAdapter_1(this, dataArrayList, R.layout.coming_movies_item_form,
                new String[] {"ItemImage", "ItemTitle", "ItemNum", "ItemText", "ItemDate"}, new int[] {R.id.movie_image,
                R.id.movie_title, R.id.movie_wtw_number, R.id.movie_wtw_text, R.id.coming_movies_time_stamp});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //to goto movie detail activity;
                String test = "hello " + position;
                System.out.println(test);
                Intent intent = new Intent();
                intent.setClass(WantedActivity.this, MovieDetailActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btn_back = (ImageButton)this.findViewById(R.id.about_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //需要后台数据
    private void setData() {
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
}
