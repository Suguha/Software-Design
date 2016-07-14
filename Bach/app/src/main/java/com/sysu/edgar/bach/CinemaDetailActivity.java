package com.sysu.edgar.bach;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by B402 on 2016/7/12.
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.MyThemeStyle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_detail);
        ImageButton btn_back = (ImageButton)this.findViewById(R.id.about_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
//            System.out.println(movies);

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
                    loading_text.setVisibility(View.GONE);
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
//                            handler_scroll.postDelayed(this, 10 * 1000);
                            e.printStackTrace();
                        }
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int j;
                                for (j = 0; j < movie_ids.length; j++) {
                                    if (movie_ids[j].equals(temp)) {
                                        Intent intent = new Intent();
                                        intent.setClass(CinemaDetailActivity.this, MovieDetailActivity.class);
                                        intent.putExtra("ItemTitle", movie_titles[j]);
                                        intent.putExtra("ItemScore", movie_scores[j]);
                                        intent.putExtra("ItemType", movie_types[j]);
                                        intent.putExtra("ItemTimeLanguage", movie_time_languages[j]);
                                        intent.putExtra("ItemOnTime", movie_on_times[j]);
                                        intent.putExtra("ItemActors", movie_actors[j]);
                                        intent.putExtra("ItemDescription", movie_descriptions[j]);
                                        intent.putExtra("ItemUrl", movie_image_urls[j]);
                                        startActivity(intent);
                                        break;
                                    }
                                }
                                if (j == movie_ids.length) {
                                    Toast.makeText(CinemaDetailActivity.this, "Movie Not Found!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        horizontal_scroll.addView(view, i);
                    }
                }
            };
            handler_scroll.postDelayed(runnable_scroll, 3 * 1000);
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
                    System.out.println(movies);
                    movies = "[{\"filmName\":\"忍者神龟2：破影而出\",\"filmId\":\"201606791406\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1464665137218.jpg\"},{\"filmName\":\"惊天魔盗团2\",\"filmId\":\"201606271308\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1463455738720.jpg\"},{\"filmName\":\"独立日：卷土重来\",\"filmId\":\"201606935475\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1463456281417.jpg\"},{\"filmName\":\"赏金猎人\",\"filmId\":\"201606833006\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1464852605744.jpg\"},{\"filmName\":\"所以……和黑粉结婚了\",\"filmId\":\"201606148706\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1466480524309.jpg\"},{\"filmName\":\"海底总动员2：多莉去哪儿\",\"filmId\":\"201511987206\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1466143965872.jpg\"},{\"filmName\":\"寒战2\",\"filmId\":\"201611751807\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1461575078160.jpg\"},{\"filmName\":\"致青春·原来你还在这里\",\"filmId\":\"201607539706\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1441529928100.jpg\"},{\"filmName\":\"三人行\",\"filmId\":\"201606771306\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1464919579548.jpg\"},{\"filmName\":\"大鱼海棠\",\"filmId\":\"201511532406\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1461121433063.jpg\"},{\"filmName\":\"摇滚藏獒\",\"filmId\":\"201607213006\",\"filmPost\":\"http://pic.spider.com.cn/pic//filmpic/jdt/1460970961465.jpg\"}]";
                    try {
                        JSONArray jsonArray = new JSONArray(movies);
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
