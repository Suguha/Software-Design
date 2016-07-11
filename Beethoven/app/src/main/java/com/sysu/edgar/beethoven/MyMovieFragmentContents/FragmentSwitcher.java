package com.sysu.edgar.beethoven.MyMovieFragmentContents;

import android.support.v4.app.Fragment;
import android.widget.Button;

import com.sysu.edgar.beethoven.R;

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
