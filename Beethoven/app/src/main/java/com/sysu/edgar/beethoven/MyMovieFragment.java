package com.sysu.edgar.beethoven;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sysu.edgar.beethoven.MyMovieFragmentContents.Coming_movies_fragment;
import com.sysu.edgar.beethoven.MyMovieFragmentContents.FragmentSwitcher;
import com.sysu.edgar.beethoven.MyMovieFragmentContents.Hot_movies_fragment;
import com.sysu.edgar.beethoven.MyMovieFragmentContents.MyPageAdapter;
import com.sysu.edgar.beethoven.MyMovieFragmentContents.Search_movies_fragment;

import java.util.ArrayList;

/**
 * Created by Edgar on 2016/7/8.
 */
public class MyMovieFragment extends android.support.v4.app.Fragment {

    private Hot_movies_fragment hot_movies_fragment = new Hot_movies_fragment();
    private Coming_movies_fragment coming_movies_fragment = new Coming_movies_fragment();
    private Search_movies_fragment search_movies_fragment = new Search_movies_fragment();
    private MyPageAdapter myPageAdapter = null;
    private Button _btn_hot = null;
    private Button _btn_coming = null;
    private Button _btn_searchMovie = null;
    private ViewPager _viewPager = null;
    private ArrayList<android.support.v4.app.Fragment> fragments;
    private FragmentSwitcher fragmentSwitcher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View movie_fragment_view = inflater.inflate(R.layout.hot_movie_fragment, container, false);
        init(getContext(), movie_fragment_view);
        _btn_hot.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));

        _viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fragmentSwitcher.processPageSelected(MyMovieFragment.this, _btn_hot, _btn_searchMovie, _btn_coming, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        _btn_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSwitcher.processPageSelected(MyMovieFragment.this, _btn_hot, _btn_searchMovie, _btn_coming, 0);
                _viewPager.setCurrentItem(0);
            }
        });

        _btn_coming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSwitcher.processPageSelected(MyMovieFragment.this, _btn_hot, _btn_searchMovie, _btn_coming, 1);
                _viewPager.setCurrentItem(1);
            }
        });

        _btn_searchMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSwitcher.processPageSelected(MyMovieFragment.this, _btn_hot, _btn_searchMovie, _btn_coming, 2);
                _viewPager.setCurrentItem(2);
            }
        });

        System.out.println("Fragment Create View!");
        return movie_fragment_view;
    }

    private ArrayList<android.support.v4.app.Fragment> getFragments() {

        ArrayList<android.support.v4.app.Fragment> fragments = new ArrayList<android.support.v4.app.Fragment>();
        fragments.add(hot_movies_fragment);
        fragments.add(coming_movies_fragment);
        fragments.add(search_movies_fragment);
        return fragments;
    }

    private void init(Context context, View view_1) {

        _btn_hot = (Button)view_1.findViewById(R.id.hot_movies);
        _btn_coming = (Button)view_1.findViewById(R.id.coming_movies);
        _btn_searchMovie = (Button)view_1.findViewById(R.id.search_movies);

        fragments = getFragments();
        myPageAdapter = new MyPageAdapter(getChildFragmentManager(), fragments);

        _viewPager = (ViewPager)view_1.findViewById(R.id.view_pager);
        _viewPager.setAdapter(myPageAdapter);

        if (_viewPager == null) {
            _viewPager.setCurrentItem(0);
        }

        if (fragmentSwitcher == null) {
            fragmentSwitcher = new FragmentSwitcher();

        }

    }
}
