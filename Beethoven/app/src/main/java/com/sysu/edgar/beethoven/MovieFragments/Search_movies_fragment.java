package com.sysu.edgar.beethoven.MovieFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.sysu.edgar.beethoven.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Edgar on 2016/7/6.
 */
public class Search_movies_fragment extends Fragment {

    private String[] btns_types = new String[] {"全部", "剧情", "喜剧", "爱情", "动画", "动作",
            "恐怖", "惊悚", "悬疑", "冒险", "科幻", "犯罪", "战争", "纪录片", "其他"};
    private String[] btns_locations = new String[] {"全部", "大陆", "美国", "法国", "英国",
            "日本", "韩国", "印度", "泰国", "港台", "德国", "其他"};
    private String[] btns_years = new String[] {"2016", "2015", "2014", "2013", "2012", "2011",
            "2000-2010", "90年代", "80年代", "70年代", "更早"};
    private String[] btns_bnh = new String[] {"好评", "最新", "热门"};
    private Button[] last_clicks = new Button[] {null, null, null, null};

    private ArrayList<HashMap<String, Object>> dataArrayList = new ArrayList<HashMap<String, Object>>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_movies_layout, container, false);

        LinearLayout linearLayout_1 = (LinearLayout)view.findViewById(R.id.filter_types);
        setButtonText(getContext(), linearLayout_1, btns_types, 0);

        LinearLayout linearLayout_2 = (LinearLayout)view.findViewById(R.id.filter_locations);
        setButtonText(getContext(), linearLayout_2, btns_locations, 1);

        LinearLayout linearLayout_3 = (LinearLayout)view.findViewById(R.id.filter_years);
        setButtonText(getContext(), linearLayout_3, btns_years, 2);

        LinearLayout linearLayout_4 = (LinearLayout)view.findViewById(R.id.filter_bnh);
        setButtonText(getContext(), linearLayout_4, btns_bnh, 3);

        ListView listView = (ListView)view.findViewById(R.id.search_movies_listview);
        getData();
        MySimpleAdapter simpleAdapter = new MySimpleAdapter(getActivity(), dataArrayList, R.layout.hot_movies_listview_layout,
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

    private void setButtonText(Context context, LinearLayout linearLayout, String[] src, final int flag) {
        for (int i = 0; i < src.length; i++) {
            final Button btn = new Button(context);
            btn.setText(src[i]);
            btn.setTextSize(12);
            if (i == 0) {
                btn.setBackground(getResources().getDrawable(R.color.colorPrimary));
                last_clicks[flag] = btn;
            } else {
                btn.setBackground(getResources().getDrawable(R.drawable.filter_btn_selector));
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo something here;
                    System.out.println("hello click");
                    if (last_clicks[flag] != null) {
                        last_clicks[flag].setBackground(getResources().getDrawable(R.drawable.filter_btn_selector));
                    }
                    btn.setBackground(getResources().getDrawable(R.color.colorPrimary));
                    last_clicks[flag] = btn;
                }
            });
            linearLayout.addView(btn);
        }
    }

    private void getData() {
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
