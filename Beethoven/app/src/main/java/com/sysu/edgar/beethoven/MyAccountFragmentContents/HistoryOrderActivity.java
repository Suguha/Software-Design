package com.sysu.edgar.beethoven.MyAccountFragmentContents;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.sysu.edgar.beethoven.MovieDetailActivity;
import com.sysu.edgar.beethoven.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Edgar on 2016/7/11.
 */
public class HistoryOrderActivity extends AppCompatActivity {

    private ListView listView = null;
    private ArrayList<HashMap<String, Object>> dataArrayList = new ArrayList<HashMap<String, Object>>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.MyThemeStyle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);

        listView = (ListView)this.findViewById(R.id.history_order_listview);
        setData();
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, dataArrayList, R.layout.history_order_item_form,
                new String[] {"ItemImage", "ItemTitle", "ItemScore", "ItemDate"}, new int[] {R.id.movie_image, R.id.movie_title,
                R.id.movie_score_number, R.id.order_date});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //to goto movie detail activity;
                String test = "hello " + position;
                System.out.println(test);
                Intent intent = new Intent();
                intent.setClass(HistoryOrderActivity.this, MovieDetailActivity.class);
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
            map.put("ItemScore", 9.6);
            map.put("ItemDate", "2016年7月11日");
            dataArrayList.add(map);
        }
    }

}
