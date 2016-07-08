package com.sysu.edgar.beethoven.MovieFragments;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sysu.edgar.beethoven.MainActivity;
import com.sysu.edgar.beethoven.MovieFragments.Coming_movies_fragment;
import com.sysu.edgar.beethoven.MovieFragments.Hot_movies_fragment;
import com.sysu.edgar.beethoven.MovieFragments.Search_movies_fragment;
import com.sysu.edgar.beethoven.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edgar on 2016/7/6.
 */
public class FragmentSwitcher {

    public  void processPageSelected(final Fragment activity, Button _btn_hot,
                                     Button _btn_searchMovie, Button _btn_coming, int position) {
        if (position == 0) {
            _btn_hot.setBackground(activity.getResources().getDrawable(R.color.colorPrimaryDark));
            _btn_coming.setBackground(activity.getResources().getDrawable(R.drawable.switch_tabs_selector));
            _btn_searchMovie.setBackground(activity.getResources().getDrawable(R.drawable.switch_tabs_selector));
        } else if (position == 1) {
            _btn_hot.setBackground(activity.getResources().getDrawable(R.drawable.switch_tabs_selector));
            _btn_coming.setBackground(activity.getResources().getDrawable(R.color.colorPrimaryDark));
            _btn_searchMovie.setBackground(activity.getResources().getDrawable(R.drawable.switch_tabs_selector));
        } else if (position == 2) {
            _btn_hot.setBackground(activity.getResources().getDrawable(R.drawable.switch_tabs_selector));
            _btn_coming.setBackground(activity.getResources().getDrawable(R.drawable.switch_tabs_selector));
            _btn_searchMovie.setBackground(activity.getResources().getDrawable(R.color.colorPrimaryDark));
        }
    }
}
