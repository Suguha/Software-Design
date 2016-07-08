package com.sysu.edgar.beethoven;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.sysu.edgar.beethoven.MovieFragments.Coming_movies_fragment;
import com.sysu.edgar.beethoven.MovieFragments.FragmentSwitcher;
import com.sysu.edgar.beethoven.MovieFragments.Hot_movies_fragment;
import com.sysu.edgar.beethoven.MovieFragments.MyPageAdapter;
import com.sysu.edgar.beethoven.MovieFragments.Search_movies_fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by B402 on 2016/7/8.
 */
public class MyMovieFragment extends android.support.v4.app.Fragment {

    private Hot_movies_fragment hot_movies_fragment = new Hot_movies_fragment();
    private Coming_movies_fragment coming_movies_fragment = new Coming_movies_fragment();
    private Search_movies_fragment search_movies_fragment = new Search_movies_fragment();
    private MyPageAdapter myPageAdapter;
    private Button _btn_hot = null;
    private Button _btn_coming = null;
    private Button _btn_searchMovie = null;
    private ViewPager _viewPager = null;
    private List<android.support.v4.app.Fragment> fragments;
    private FragmentSwitcher fragmentSwitcher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view_1 = inflater.inflate(R.layout.movies_fragment_layout, container, false);
        init(getContext(), view_1);
        _viewPager.setAdapter(myPageAdapter);
        _viewPager.setCurrentItem(0);
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
        return view_1;
    }

    @Override
    public void onStart() {
        System.out.println("Fragment Start!");
        super.onStart();
    }

    @Override
    public void onResume() {
        System.out.println("Fragment Resume!");
        super.onResume();
    }

    @Override
    public void onPause() {
        System.out.println("Fragment Pause!");
        super.onPause();
    }

    @Override
    public void onStop() {
        System.out.println("Fragment Stop!");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        System.out.println("Fragment Destroy View!");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        System.out.println("Fragment Destroy!");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        System.out.println("Fragment Detach!");
        super.onDetach();
    }

    @Override
    public void onAttach(Activity activity) {
        System.out.println("Fragment Attach!");
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        System.out.println("Fragment Attach-1!");
        super.onAttach(context);
    }

    private List<android.support.v4.app.Fragment> getFragments() {

        if (hot_movies_fragment == null) {
            hot_movies_fragment = new Hot_movies_fragment();
            System.out.println("Hello new Hot_movies_fragment()");
        }

        if (coming_movies_fragment == null) {
            coming_movies_fragment = new Coming_movies_fragment();
        }

        if (search_movies_fragment == null) {
            search_movies_fragment = new Search_movies_fragment();
        }

        List<android.support.v4.app.Fragment> fragments = new ArrayList<android.support.v4.app.Fragment>();
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
        if (myPageAdapter == null) {
            myPageAdapter = new MyPageAdapter(getFragmentManager(), fragments);

        }
        _viewPager = (ViewPager)view_1.findViewById(R.id.view_pager);

        if (fragmentSwitcher == null) {
            fragmentSwitcher = new FragmentSwitcher();

        }

    }
}
