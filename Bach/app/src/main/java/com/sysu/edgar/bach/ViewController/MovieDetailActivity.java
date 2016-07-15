package com.sysu.edgar.bach.ViewController;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sysu.edgar.bach.R;

/**
 * Created by Edgar on 2016/7/11.
 */
public class MovieDetailActivity extends AppCompatActivity {
    private final static String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/Download/Bach/";
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
            Bundle bundle = getIntent().getExtras();
            TextView movie_title = (TextView)this.findViewById(R.id.detail_item_title);
            TextView movie_score = (TextView)this.findViewById(R.id.detail_item_score);
            TextView movie_type = (TextView)this.findViewById(R.id.detail_item_type);
            TextView movie_time_language = (TextView)this.findViewById(R.id.detail_item_time_language);
            TextView movie_on_time = (TextView)this.findViewById(R.id.detail_item_on_time);
            TextView movie_actors = (TextView)this.findViewById(R.id.detail_item_actors);
            TextView movie_description = (TextView)this.findViewById(R.id.synopsis_text);
            final ImageView movie_image = (ImageView)this.findViewById(R.id.detail_item_image);

            movie_title.setText(bundle.getString("ItemTitle"));
            movie_score.setText(bundle.getString("ItemScore"));
            movie_type.setText(bundle.getString("ItemType"));
            movie_time_language.setText(bundle.getString("ItemTimeLanguage"));
            movie_on_time.setText(bundle.getString("ItemOnTime"));
            movie_actors.setText(bundle.getString("ItemActors"));
            movie_description.setText(bundle.getString("ItemDescription"));
            final String movie_id = bundle.getString("ItemId");
            final String url = bundle.getString("ItemUrl");
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(ALBUM_PATH + movie_id + ".png");
                movie_image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
