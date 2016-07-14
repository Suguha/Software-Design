package com.sysu.edgar.bach;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Edgar on 2016/7/14.
 */
public class SessionFragment extends Fragment {

    private LinearLayout linearLayout = null;
    private String BASE_URL = "http://119.29.144.22:8001/api/times?date=2016-06-16&cinemaId=44008701&movieId=201603786108";
    private SessionItems items = new SessionItems(BASE_URL);

    public int height = 130 * items.length;

    private Handler handler_session = new Handler();
    private Runnable runnable_session;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session_form, container, false);
        System.out.println("!!!!!!!!!!hello new fragment!" + 130 * items.length);
        linearLayout = (LinearLayout) view.findViewById(R.id.session_fragment_layout);
        final LayoutInflater finalInflater = inflater;
        final TextView load_session = (TextView)view.findViewById(R.id.loading_session_text);
        runnable_session = new Runnable() {
            @Override
            public void run() {
                if (items.count != items.length || items.length == 0) {
                    linearLayout.setVisibility(View.INVISIBLE);
                    handler_session.postDelayed(this, 5 * 1000);
                } else {
                    load_session.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    for (int i = 0; i < items.length; i++) {
                        View view_item = new View(getContext());
                        view_item = finalInflater.inflate(R.layout.session_item_form, null, false);
                        TextView start_time = (TextView)view_item.findViewById(R.id.start_time);
                        TextView end_Time = (TextView)view_item.findViewById(R.id.end_time);
                        TextView language_effect = (TextView)view_item.findViewById(R.id.language_and_effect);
                        TextView playing_room = (TextView)view_item.findViewById(R.id.playing_room);
                        TextView price = (TextView)view_item.findViewById(R.id.ticket_price);

                        start_time.setText(items.startTimes[i]);
                        end_Time.setText(items.endTimes[i]);
                        language_effect.setText(items.languageAndEffects[i]);
                        playing_room.setText(items.playingRooms[i]);
                        price.setText(items.prices[i]);

                        linearLayout.addView(view_item, i);
                    }
                }
            }
        };
        handler_session.postDelayed(runnable_session, 5 * 1000);
        return view;
    }

    @Override
    public void onDestroy() {
        handler_session.removeCallbacks(runnable_session);
        super.onDestroy();
    }
}
