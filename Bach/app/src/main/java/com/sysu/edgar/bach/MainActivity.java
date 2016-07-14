package com.sysu.edgar.bach;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MoviesFragment moviesFragment = new MoviesFragment();;
    private CinemaFragment cinemaFragment = new CinemaFragment();
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    public MyPagerAdapter myPagerAdapter = null;
    private ViewPager viewPager = null;
    private TextView movie_text;
    private TextView cinema_text;
    private ImageView movie_img;
    private ImageView cinema_img;
    private LinearLayout bottom_btn_movies;
    private LinearLayout bottom_btn_cinemas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movie_text = (TextView)this.findViewById(R.id.bottom_text_movies);
        cinema_text = (TextView)this.findViewById(R.id.bottom_text_cinemas);
        movie_img = (ImageView)this.findViewById(R.id.bottom_ic_movies);
        cinema_img = (ImageView)this.findViewById(R.id.bottom_ic_cinemas);
        bottom_btn_movies = (LinearLayout)this.findViewById(R.id.bottom_btn_movies);
        bottom_btn_cinemas = (LinearLayout)this.findViewById(R.id.bottom_btn_cinemas);

        setFragments();
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager = (ViewPager)this.findViewById(R.id.main_view_pager);
        viewPager.setAdapter(myPagerAdapter);
        changeColor(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottom_btn_movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(0);
                viewPager.setCurrentItem(0);
            }
        });

        bottom_btn_cinemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(1);
                viewPager.setCurrentItem(1);
            }
        });
    }

    private void setFragments() {
        fragments.clear();
        fragments.add(moviesFragment);
        fragments.add(cinemaFragment);
    }

    private void changeColor(int position) {
        switch (position) {
            case 0:
                movie_text.setTextColor(0xffffffff);
                cinema_text.setTextColor(0xde000000);
                movie_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_movie_white_24dp));
                cinema_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_cinema_black_24dp));
                bottom_btn_movies.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                bottom_btn_cinemas.setBackground(getResources().getDrawable(R.drawable.switch_tabs_selector));
                moviesFragment.refreshMovies();
                break;

            case 1:
                movie_text.setTextColor(0xde000000);
                cinema_text.setTextColor(0xffffffff);
                movie_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_movie_black_24dp));
                cinema_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_cinema_white_24dp));
                bottom_btn_movies.setBackground(getResources().getDrawable(R.drawable.switch_tabs_selector));
                bottom_btn_cinemas.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
                cinemaFragment.setMovieDataBase(moviesFragment.movieDatabase);
                break;

            default:
                break;
        }
    }
}
