package com.sysu.edgar.beethoven.MovieFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sysu.edgar.beethoven.R;

/**
 * Created by B402 on 2016/7/6.
 */
public class Search_movies_fragment extends Fragment {

    private String[] btns_types = new String[] {"全部", "剧情", "喜剧", "爱情", "动画", "动作",
            "恐怖", "惊悚", "悬疑", "冒险", "科幻", "犯罪", "战争", "纪录片", "其他"};
    //private String btns_locations

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_movies_layout, container, false);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
