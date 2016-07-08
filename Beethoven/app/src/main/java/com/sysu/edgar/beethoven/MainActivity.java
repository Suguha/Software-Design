package com.sysu.edgar.beethoven;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private View loginView, regView;
    private LinearLayout _search_layout = null;
    private boolean first_click = true;
    private boolean has_login = false;
    private ImageButton _bottom_btn_movies = null;
    private ImageButton _bottom_btn_cinemas = null;
    private ImageButton _bottom_btn_account = null;
    private EditText search_text = null;
    private ImageButton btn_search = null;
    private BottomMenuSwitch bottomMenuSwitch;
    private SearchAdaptive searchAdaptive;
    private WindowManager wm;
    private int width;
    private int height;
    private int h;
    private int w;
    private FragmentManager fragmentManager;
    private MyCinemaFragment myCinemaFragment;
    private MyAccountFragment myAccountFragment;
    private MyMovieFragment myMovieFragment = null;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setDefaultFragment();
        myMovieFragment.setRetainInstance(true);

        _bottom_btn_movies.setImageDrawable(getResources().getDrawable(R.drawable.ic_movie_white_24dp));
        _bottom_btn_movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to change activity here
                if (!first_click) {
                    searchAdaptive.hide(_search_layout);
                    first_click = true;
                }
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                if (myMovieFragment == null) {
                    myMovieFragment = new MyMovieFragment();
                    System.out.println("Hello new MyMovieFragment()");
                }
                bottomMenuSwitch.movieSelected(MainActivity.this, _bottom_btn_movies, _bottom_btn_cinemas, _bottom_btn_account);
                fragmentTransaction.replace(R.id.bottom_fragment_content, myMovieFragment);
                fragmentTransaction.commit();
            }
        });

        _bottom_btn_cinemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to change activity here
                if (!first_click) {
                    searchAdaptive.hide(_search_layout);
                    first_click = true;
                }
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                if (myCinemaFragment == null) {
                    myCinemaFragment = new MyCinemaFragment();
                }
                bottomMenuSwitch.cinemaSelected(MainActivity.this, _bottom_btn_movies, _bottom_btn_cinemas, _bottom_btn_account);
                fragmentTransaction.replace(R.id.bottom_fragment_content, myCinemaFragment);
                fragmentTransaction.commit();
            }
        });

        _bottom_btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to change activity here
                if (!first_click) {
                    searchAdaptive.hide(_search_layout);
                    first_click = true;
                }
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                if (myAccountFragment == null) {
                    myAccountFragment = new MyAccountFragment();
                }
                bottomMenuSwitch.accountSelected(MainActivity.this, _bottom_btn_movies, _bottom_btn_cinemas, _bottom_btn_account);
                fragmentTransaction.replace(R.id.bottom_fragment_content, myAccountFragment);
                fragmentTransaction.commit();
            }
        });


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAdaptive.hide(_search_layout);
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
                    searchAdaptive.hide(_search_layout);
                    first_click = true;

                    //todo search thing here
                }

                return false;
            }
        });

    }

    private void setDefaultFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        if (myMovieFragment == null) {
            myMovieFragment = new MyMovieFragment();
            System.out.println("Hello MyMovieFragment()");
        }
        fragmentTransaction.replace(R.id.bottom_fragment_content, myMovieFragment);
        fragmentTransaction.commit();
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
                    searchAdaptive.show(_search_layout);
                    first_click = false;
                } else {
                    searchAdaptive.hide(_search_layout);
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
        _search_layout = (LinearLayout)this.findViewById(R.id.search_layout);
        btn_search = (ImageButton)this.findViewById(R.id.btn_search);
        _bottom_btn_account = (ImageButton)this.findViewById(R.id.bottom_btn_account);
        _bottom_btn_cinemas = (ImageButton)this.findViewById(R.id.bottom_btn_cinemas);
        _bottom_btn_movies = (ImageButton)this.findViewById(R.id.bottom_btn_movies);

        loginView = LayoutInflater.from(this).inflate(R.layout.login_layout, null);
        regView = LayoutInflater.from(this).inflate(R.layout.register_layout, null);

        bottomMenuSwitch = new BottomMenuSwitch();
        searchAdaptive = new SearchAdaptive();

    }
}
