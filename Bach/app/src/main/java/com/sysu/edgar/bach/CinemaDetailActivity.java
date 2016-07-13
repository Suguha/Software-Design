package com.sysu.edgar.bach;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by B402 on 2016/7/12.
 */
public class CinemaDetailActivity extends AppCompatActivity {

    private LinearLayout horizontal_scroll = null;
    private String movies = new String();

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
            System.out.println(movies);

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
        }
    }

    private void setHorizontalData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (movies.length() != 0) {
                    try {
                        JSONArray jsonArray = new JSONArray(movies);
                        JSONObject jsonObject = new JSONObject(movies);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            View view = new View(getBaseContext());
                            view = getLayoutInflater().inflate(R.layout.horizontal_scroll_item_form, null, false);
                            TextView title = (TextView)view.findViewById(R.id.scroll_item_title);
                            ImageView image = (ImageView)view.findViewById(R.id.scroll_item_image);

                            title.setText(object.getString("filmName"));
                            Bitmap bitmap = ImageService.getBitmapFromUrl(object.getString("filmPost"));
                            image.setImageBitmap(bitmap);
                            horizontal_scroll.addView(view, i);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Empty Movies!!!!!!!!!!!!!!!!!!!!!");
                }
            }
        }).start();
    }
}
