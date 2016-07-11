package com.sysu.edgar.beethoven.MyAccountFragmentContents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sysu.edgar.beethoven.R;

/**
 * Created by Administrator on 2016/7/11.
 */
public class HistoryOrderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.MyThemeStyle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
    }
}
