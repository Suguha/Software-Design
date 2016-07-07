package com.sysu.edgar.beethoven;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sysu.edgar.beethoven.MovieFragments.Coming_movies_fragment;
import com.sysu.edgar.beethoven.MovieFragments.FragmentSwitcher;
import com.sysu.edgar.beethoven.MovieFragments.Hot_movies_fragment;
import com.sysu.edgar.beethoven.MovieFragments.MyPageAdapter;
import com.sysu.edgar.beethoven.MovieFragments.Search_movies_fragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private View loginView, regView;
    private RelativeLayout _search_layout = null;
    private ImageView _separate_line = null;
    RelativeLayout.LayoutParams _lp = null;
    private boolean first_click = true;
    private boolean has_login = false;
    private Hot_movies_fragment hot_movies_fragment;
    private Coming_movies_fragment coming_movies_fragment;
    private Search_movies_fragment search_movies_fragment;
    private MyPageAdapter myPageAdapter;
    private Button _btn_hot = null;
    private Button _btn_coming = null;
    private Button _btn_searchMovie = null;
    private ViewPager _viewPager = null;
    private ImageButton _bottom_btn_movies = null;
    private ImageButton _bottom_btn_cinemas = null;
    private ImageButton _bottom_btn_account = null;
    private EditText search_text = null;
    private ImageButton btn_search = null;
    private List<Fragment> fragments;
    private BottomMenuSwitch bottomMenuSwitch = null;
    private SearchAdaptive searchAdaptive = null;
    private FragmentSwitcher fragmentSwitcher = null;
    private WindowManager wm;
    private int width;
    private int height;
    private int h;
    private int w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        _bottom_btn_movies.setImageDrawable(getResources().getDrawable(R.drawable.ic_movie_white_24dp));
        _bottom_btn_movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to change activity here
                bottomMenuSwitch.movieSelected(MainActivity.this, _bottom_btn_movies, _bottom_btn_cinemas, _bottom_btn_account);
            }
        });

        _bottom_btn_cinemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to change activity here
                bottomMenuSwitch.cinemaSelected(MainActivity.this, _bottom_btn_movies, _bottom_btn_cinemas, _bottom_btn_account);
            }
        });

        _bottom_btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to change activity here
                bottomMenuSwitch.accountSelected(MainActivity.this, _bottom_btn_movies, _bottom_btn_cinemas, _bottom_btn_account);
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAdaptive.hide(_search_layout, _separate_line, _lp);
                first_click = true;
            }
        });

        search_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {

                    //隐藏键盘
                    ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            MainActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS
                    );
                    searchAdaptive.hide(_search_layout, _separate_line, _lp);
                    first_click = true;

                    //todo search thing here
                }

                return false;
            }
        });

        final FragmentSwitcher fragmentSwitcher = new FragmentSwitcher();
        _viewPager.setAdapter(myPageAdapter);
        _viewPager.setCurrentItem(0);
        _btn_hot.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));

        _viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fragmentSwitcher.processPageSelected(MainActivity.this, _btn_hot, _btn_searchMovie, _btn_coming, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        _btn_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSwitcher.processPageSelected(MainActivity.this, _btn_hot, _btn_searchMovie, _btn_coming, 0);
                _viewPager.setCurrentItem(0);
            }
        });

        _btn_coming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSwitcher.processPageSelected(MainActivity.this, _btn_hot, _btn_searchMovie, _btn_coming, 1);
                _viewPager.setCurrentItem(1);
            }
        });

        _btn_searchMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSwitcher.processPageSelected(MainActivity.this, _btn_hot, _btn_searchMovie, _btn_coming, 2);
                _viewPager.setCurrentItem(2);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //todo something when item selected
        switch (item.getItemId()) {
            case R.id.menu_search:
                Toast.makeText(MainActivity.this, "Hello Search", Toast.LENGTH_SHORT).show();
                if (first_click) {
                    searchAdaptive.show(_search_layout, _separate_line, _lp);
                    first_click = false;
                } else {
                    searchAdaptive.hide(_search_layout, _separate_line, _lp);
                    first_click = true;
                }
                break;

            case R.id.menu_account:
                if (has_login) {
                    //todo log out
                    backgroundAlpha(1.0f);

                } else {
                    //todo log in
                    final LoginActivity loginActivity = new LoginActivity();
                    backgroundAlpha(0.3f);
                    loginActivity.proc_login(loginView, regView, MainActivity.this, w, h);
//                    has_login = true;
                }
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }










    //这里往下是初始化信息，可不看
    private List<Fragment> getFragments() {
        hot_movies_fragment = new Hot_movies_fragment();
        coming_movies_fragment = new Coming_movies_fragment();
        search_movies_fragment = new Search_movies_fragment();

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(hot_movies_fragment);
        fragments.add(coming_movies_fragment);
        fragments.add(search_movies_fragment);
        return fragments;
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha;
        getWindow().setAttributes(layoutParams);
    }

    public void init() {
        wm = (WindowManager)getBaseContext().getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        w = width * 8 / 10;
        h = height * 7 / 24;

        search_text = (EditText)this.findViewById(R.id.search_text);
        _search_layout = (RelativeLayout)this.findViewById(R.id.search_layout);
        _separate_line = (ImageView) this.findViewById(R.id.separate_line);
        _lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btn_search = (ImageButton)this.findViewById(R.id.btn_search);
        _btn_hot = (Button)this.findViewById(R.id.hot_movies);
        _btn_coming = (Button)this.findViewById(R.id.coming_movies);
        _btn_searchMovie = (Button)this.findViewById(R.id.search_movies);
        _bottom_btn_account = (ImageButton)this.findViewById(R.id.bottom_btn_account);
        _bottom_btn_cinemas = (ImageButton)this.findViewById(R.id.bottom_btn_cinemas);
        _bottom_btn_movies = (ImageButton)this.findViewById(R.id.bottom_btn_movies);

        loginView = LayoutInflater.from(this).inflate(R.layout.login_layout, null);
        regView = LayoutInflater.from(this).inflate(R.layout.register_layout, null);

        fragments = getFragments();
        myPageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        _viewPager = (ViewPager)this.findViewById(R.id.view_pager);

        bottomMenuSwitch = new BottomMenuSwitch();
        searchAdaptive = new SearchAdaptive();
        fragmentSwitcher = new FragmentSwitcher();
    }
}
