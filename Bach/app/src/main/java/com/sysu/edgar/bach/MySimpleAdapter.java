package com.sysu.edgar.bach;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Edgar on 2016/7/7.
 */
public class MySimpleAdapter extends SimpleAdapter {

    private ArrayList<Bitmap> BMImages = new ArrayList<Bitmap>();

    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        final ImageView imageView = (ImageView)view.findViewById(R.id.movie_image);

        if (BMImages.size() != 0) {
            imageView.setImageBitmap(BMImages.get(position));
        }

        Button btn = (Button) view.findViewById(R.id.btn_buy_tickets);
        btn.setTag(position);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to goto buy ticket activity;
                System.out.println("Hello Click");
            }
        });
        return view;
    }

    public void setBMImages(ArrayList<Bitmap> bmImages) {
        this.BMImages = bmImages;
    }


}
