package com.sysu.edgar.beethoven;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Edgar on 2016/7/6.
 */
public class SearchAdaptive extends MainActivity {
    public void hide(RelativeLayout _search_layout, ImageView _separate_line, RelativeLayout.LayoutParams _lp) {
        _search_layout.setVisibility(View.INVISIBLE);
        _lp.topMargin = 0;
        _separate_line.setLayoutParams(_lp);
    }

    public void show(RelativeLayout _search_layout, ImageView _separate_line, RelativeLayout.LayoutParams _lp) {
        _search_layout.setVisibility(View.VISIBLE);
        _lp.topMargin = 100;
        _separate_line.setLayoutParams(_lp);
    }
}
