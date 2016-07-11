package com.sysu.edgar.beethoven;

import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Edgar on 2016/7/11.
 */
public class MovieDetailActivity extends AppCompatActivity {

    private boolean isCollapse = true;

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

        final ImageView image_load_more = (ImageView) this.findViewById(R.id.loading_more);
        final TextView synopsis_text = (TextView)this.findViewById(R.id.synopsis_text);
        synopsis_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 200));
        synopsis_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCollapse) {
                    isCollapse = false;
                    image_load_more.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                    synopsis_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                } else {
                    isCollapse = true;
                    image_load_more.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
                    synopsis_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 200));
                }
            }
        });

        //还有其他数据如电影名称等信息没设置，根据后台修改

    }
}
