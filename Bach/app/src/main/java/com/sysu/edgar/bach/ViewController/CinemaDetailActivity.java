package com.sysu.edgar.bach.ViewController;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sysu.edgar.bach.Adapter.MyTabPagerAdapter;
import com.sysu.edgar.bach.Network.ImageService;
import com.sysu.edgar.bach.R;
import com.sysu.edgar.bach.ViewController.Fragments.SessionFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Edgar on 2016/7/12.
 */
public class CinemaDetailActivity extends AppCompatActivity {

    private LinearLayout horizontal_scroll = null;
    private String movies = new String();
    private ArrayList<Bitmap> scrollImages = new ArrayList<Bitmap>();
    private String[] film_titles;
    private String[] film_ids;
    private Handler handler_scroll = new Handler();
    private Runnable runnable_scroll = null;

    private String[] movie_titles;
    private String[] movie_scores;
    private String[] movie_types;
    private String[] movie_time_languages;
    private String[] movie_on_times;
    private String[] movie_actors;
    private String[] movie_descriptions;
    private String[] movie_ids;
    private String[] movie_image_urls;

    private TabLayout mTabLayout;
    private MyTabPagerAdapter myTabPagerAdapter;
    private ViewPager mTabViewPager;
    private ArrayList<Fragment> tabFragments = new ArrayList<Fragment>();
    private ArrayList<String> tabTitles = new ArrayList<String>();

    private LinearLayout detail_item_layout;
    private TextView select_title;
    private TextView select_score;
    private TextView select_tl;
    private JSONArray jsonArray;

    private int position;
    private View last_click_view = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.MyThemeStyle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_detail);

        detail_item_layout = (LinearLayout)this.findViewById(R.id.cinema_detail_item);
        select_title = (TextView)this.findViewById(R.id.cinema_detail_select_name);
        select_score = (TextView)this.findViewById(R.id.cinema_detail_select_score);
        select_tl = (TextView)this.findViewById(R.id.cinema_detail_select_tl);
        detail_item_layout.setVisibility(View.INVISIBLE);

        ImageButton btn_back = (ImageButton)this.findViewById(R.id.about_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTabLayout = (TabLayout)this.findViewById(R.id.session_tab_layout);
        mTabViewPager = (ViewPager) this.findViewById(R.id.session_view_pager);

        tabFragments.add(new SessionFragment());
//        tabFragments.add(new SessionFragment());
//        tabFragments.add(new SessionFragment());

        tabTitles.add("06-16");
//        tabTitles.add("Tab2");
//        tabTitles.add("Tab3");

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitles.get(0)));
//        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitles.get(1)));
//        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitles.get(2)));

        myTabPagerAdapter = new MyTabPagerAdapter(this.getSupportFragmentManager(), tabFragments, tabTitles);

        final TextView loading_text = (TextView)this.findViewById(R.id.loading_text);
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            TextView cinema_name = (TextView)this.findViewById(R.id.cinema_name);
            TextView cinema_location = (TextView)this.findViewById(R.id.cinema_address);
            TextView cinema_transport = (TextView)this.findViewById(R.id.cinema_transport);
            TextView cinema_tel = (TextView)this.findViewById(R.id.cinema_telephone);
            TextView cinema_url = (TextView)this.findViewById(R.id.cinema_url);

            cinema_name.setText(bundle.getString("CinemaName"));
            cinema_location.setText(bundle.getString("CinemaLocation"));
            cinema_transport.setText(bundle.getString("CinemaTransport"));
            cinema_tel.setText(bundle.getString("CinemaTel"));
            cinema_url.setText(Html.fromHtml("<u>" + bundle.getString("CinemaUrl") + "</u>"));
            movies = bundle.getString("CinemaMovies");

            movie_titles = bundle.getStringArray("DatabaseTitles");
            movie_scores = bundle.getStringArray("DatabaseScores");
            movie_types = bundle.getStringArray("DatabaseTypes");
            movie_time_languages = bundle.getStringArray("DatabaseTimeLanguages");
            movie_on_times = bundle.getStringArray("DatabaseOnTimes");
            movie_actors = bundle.getStringArray("DatabaseActors");
            movie_descriptions = bundle.getStringArray("DatabaseDescriptions");
            movie_ids = bundle.getStringArray("DatabaseIds");
            movie_image_urls = bundle.getStringArray("DatabaseImgUrls");

            final Uri uri = Uri.parse(bundle.getString("CinemaUrl"));
            cinema_url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
            horizontal_scroll = (LinearLayout)this.findViewById(R.id.cinema_detail_horizontal_scroll);
            setHorizontalData();
            runnable_scroll = new Runnable() {
                @Override
                public void run() {
                    try {
                        if (scrollImages.size() != jsonArray.length() || jsonArray == null) {
                            horizontal_scroll.setVisibility(View.INVISIBLE);
                            handler_scroll.postDelayed(this, 5 * 1000);
                        } else {
                            loading_text.setVisibility(View.GONE);
                            horizontal_scroll.setVisibility(View.VISIBLE);
                            for (int i = 0; i < film_ids.length; i++) {
                                View view = new View(getBaseContext());
                                view = getLayoutInflater().inflate(R.layout.horizontal_scroll_item_form, null, false);
                                TextView title = (TextView)view.findViewById(R.id.scroll_item_title);
                                ImageView image = (ImageView)view.findViewById(R.id.scroll_item_image);
                                title.setText(film_titles[i]);
                                final String temp = film_ids[i];
                                try {
                                    image.setImageBitmap(scrollImages.get(i));
                                } catch (IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }
                                final View finalView = view;
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finalView.setBackground(getResources().getDrawable(R.color.colorAccent));
                                        if (last_click_view != null) {
                                            last_click_view.setBackground(getResources().getDrawable(R.drawable.item_click_selector));
                                        }
                                        last_click_view = finalView;
                                        int j;
                                        for (j = 0; j < movie_ids.length; j++) {
                                            if (movie_ids[j].equals(temp)) {
                                                position = j;
                                                select_title.setText(movie_titles[j]);
                                                select_score.setText(movie_scores[j] + "分");
                                                select_tl.setText(movie_time_languages[j]);
                                                detail_item_layout.setVisibility(View.VISIBLE);
                                                break;
                                            }
                                        }
                                        if (j == movie_ids.length) {
                                            Toast.makeText(CinemaDetailActivity.this, "Movie Not Found!", Toast.LENGTH_SHORT).show();
                                            detail_item_layout.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                });
                                horizontal_scroll.addView(view, i);
                            }
                        }
                    } catch (NullPointerException ek) {
                        Toast.makeText(CinemaDetailActivity.this, "Empty movie list!", Toast.LENGTH_SHORT).show();
                        ek.printStackTrace();
                    }
                }
            };
            handler_scroll.postDelayed(runnable_scroll, 5 * 1000);
            mTabViewPager.setAdapter(myTabPagerAdapter);
            mTabLayout.setupWithViewPager(mTabViewPager);

            detail_item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (detail_item_layout.getVisibility() == View.VISIBLE) {
                        Intent intent = new Intent();
                        intent.setClass(CinemaDetailActivity.this, MovieDetailActivity.class);
                        intent.putExtra("ItemTitle", movie_titles[position]);
                        intent.putExtra("ItemScore", movie_scores[position]);
                        intent.putExtra("ItemType", movie_types[position]);
                        intent.putExtra("ItemTimeLanguage", movie_time_languages[position]);
                        intent.putExtra("ItemOnTime", movie_on_times[position]);
                        intent.putExtra("ItemActors", movie_actors[position]);
                        intent.putExtra("ItemDescription", movie_descriptions[position]);
                        intent.putExtra("ItemUrl", movie_image_urls[position]);
                        intent.putExtra("ItemId", movie_ids[position]);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        handler_scroll.removeCallbacks(runnable_scroll);
        super.onDestroy();
    }

    private void setHorizontalData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (movies.length() != 0) {
                    movies.replaceAll("\\\\", "");
                    System.out.println("1" + movies);
                    movies = "[{\"filmName\":\"忍者神龟2：破影而出\",\"filmId\":\"201606791406\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1464665137218.jpg\"},{\"filmName\":\"惊天魔盗团2\",\"filmId\":\"201606271308\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1463455738720.jpg\"},{\"filmName\":\"独立日：卷土重来\",\"filmId\":\"201606935475\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1463456281417.jpg\"},{\"filmName\":\"赏金猎人\",\"filmId\":\"201606833006\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1464852605744.jpg\"},{\"filmName\":\"所以……和黑粉结婚了\",\"filmId\":\"201606148706\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1466480524309.jpg\"},{\"filmName\":\"海底总动员2：多莉去哪儿\",\"filmId\":\"201511987206\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1466143965872.jpg\"},{\"filmName\":\"寒战2\",\"filmId\":\"201611751807\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1461575078160.jpg\"},{\"filmName\":\"致青春·原来你还在这里\",\"filmId\":\"201607539706\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1441529928100.jpg\"},{\"filmName\":\"三人行\",\"filmId\":\"201606771306\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1464919579548.jpg\"},{\"filmName\":\"大鱼海棠\",\"filmId\":\"201511532406\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1461121433063.jpg\"},{\"filmName\":\"摇滚藏獒\",\"filmId\":\"201607213006\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1460970961465.jpg\"}]";
                    System.out.println("2" + movies);
                    try {
                        jsonArray = new JSONArray(movies);
                        film_titles = new String[jsonArray.length()];
                        film_ids = new String[jsonArray.length()];
                        scrollImages.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            film_titles[i] = object.getString("filmName");
                            film_ids[i] = object.getString("filmId");
                            Bitmap bitmap = ImageService.getBitmapFromUrl(object.getString("filmPost"));
                            scrollImages.add(i, bitmap);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
