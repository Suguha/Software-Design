package com.sysu.edgar.bach;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

/**
 * Created by Edgar on 2016/7/11.
 */
public class MovieDetailActivity extends AppCompatActivity {

    private boolean isCollapse = true;
    private static final String BASE_URL = "http://119.29.144.22:8001/api/movie";
    private JSONArray jsonArray;
    private int movieIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.MyThemeStyle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ImageButton btn_back = (ImageButton)this.findViewById(R.id.about_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent() != null && getIntent().getExtras() != null) {
            //还有其他数据如电影名称等信息没设置，根据后台修改

            TextView movie_title = (TextView)this.findViewById(R.id.detail_item_title);
            TextView movie_score = (TextView)this.findViewById(R.id.detail_item_score);
            TextView movie_type = (TextView)this.findViewById(R.id.detail_item_type);
            TextView movie_time_language = (TextView)this.findViewById(R.id.detail_item_time_language);
            TextView movie_on_time = (TextView)this.findViewById(R.id.detail_item_on_time);
            TextView movie_actors = (TextView)this.findViewById(R.id.detail_item_actors);
            TextView movie_description = (TextView)this.findViewById(R.id.synopsis_text);
            ImageView movie_image = (ImageView)this.findViewById(R.id.detail_item_image);

            movie_title.setText(getIntent().getExtras().getString("ItemTitle"));
            movie_score.setText(getIntent().getExtras().getString("ItemScore"));
            movie_type.setText(getIntent().getExtras().getString("ItemType"));
            movie_time_language.setText(getIntent().getExtras().getString("ItemTimeLanguage"));
            movie_on_time.setText(getIntent().getExtras().getString("ItemOnTime"));
            movie_actors.setText(getIntent().getExtras().getString("ItemActors"));
            movie_description.setText(getIntent().getExtras().getString("ItemDescription"));

            byte[] data = getIntent().getExtras().getByteArray("ItemImage");
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            movie_image.setImageBitmap(bitmap);
        }
    }
}
