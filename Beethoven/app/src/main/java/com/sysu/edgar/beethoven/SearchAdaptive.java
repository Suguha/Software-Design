package com.sysu.edgar.beethoven;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Edgar on 2016/7/6.
 */
public class SearchAdaptive extends MainActivity {
    public void hide(LinearLayout _search_layout) {
        _search_layout.setVisibility(View.GONE);
    }

    public void show(LinearLayout _search_layout) {
        _search_layout.setVisibility(View.VISIBLE);
    }
}
