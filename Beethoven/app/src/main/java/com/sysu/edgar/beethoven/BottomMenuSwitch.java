package com.sysu.edgar.beethoven;

import android.widget.ImageButton;

/**
 * Created by B402 on 2016/7/6.
 */
public class BottomMenuSwitch {
    public void movieSelected(MainActivity activity, ImageButton _bottom_btn_movies, ImageButton _bottom_btn_cinemas, ImageButton _bottom_btn_account) {
        _bottom_btn_movies.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_movie_white_24dp));
        _bottom_btn_cinemas.setImageDrawable(activity.getResources().getDrawable(R.drawable.bottom_cinema_selector));
        _bottom_btn_account.setImageDrawable(activity.getResources().getDrawable(R.drawable.bottom_account_selector));
    }
    public void cinemaSelected(MainActivity activity, ImageButton _bottom_btn_movies, ImageButton _bottom_btn_cinemas, ImageButton _bottom_btn_account) {
        _bottom_btn_movies.setImageDrawable(activity.getResources().getDrawable(R.drawable.bottom_movies_selector));
        _bottom_btn_cinemas.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_cinema_white_24dp));
        _bottom_btn_account.setImageDrawable(activity.getResources().getDrawable(R.drawable.bottom_account_selector));
    }
    public void accountSelected(MainActivity activity, ImageButton _bottom_btn_movies, ImageButton _bottom_btn_cinemas, ImageButton _bottom_btn_account) {
        _bottom_btn_movies.setImageDrawable(activity.getResources().getDrawable(R.drawable.bottom_movies_selector));
        _bottom_btn_cinemas.setImageDrawable(activity.getResources().getDrawable(R.drawable.bottom_cinema_selector));
        _bottom_btn_account.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_account_circle_white_24dp));
    }
}
