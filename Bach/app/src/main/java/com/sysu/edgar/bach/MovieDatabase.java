package com.sysu.edgar.bach;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by B402 on 2016/7/13.
 */
public class MovieDatabase {

    public String[] titles;
    public String[] scores;
    public String[] types;
    public String[] time_languages;
    public String[] on_times;
    public String[] actors;
    public Bitmap[] images;
    public String[] descriptions;
    public String[] ids;
    public String[] imgUrls;
    private JSONArray jsonArray;
    public int length = 0;

    public MovieDatabase(MovieDatabase other) {
        this.titles = other.titles;
        this.scores = other.scores;
        this.types = other.types;
        this.time_languages = other.time_languages;
        this.on_times = other.on_times;
        this.actors = other.actors;
        this.descriptions = other.descriptions;
        this.ids = other.ids;
        this.imgUrls = other.imgUrls;
        this.images = other.images;
        this.length = other.length;
    }

    public MovieDatabase() {
        length = 0;
    }

    public MovieDatabase(final String urlPath) {
//        final CountDownLatch latch = new CountDownLatch(1);
        System.out.println("Loading movies...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jsonArray = JsonParse.getJsonArray(urlPath);
                    length = jsonArray.length();
                    titles = new String[length];
                    scores = new String[length];
                    types = new String[length];
                    time_languages = new String[length];
                    on_times = new String[length];
                    actors = new String[length];
                    descriptions = new String[length];
                    ids = new String[length];
                    imgUrls = new String[length];
                    for (int i = 0; i < length; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        titles[i] = object.getString("name");
                        scores[i] = object.getString("score");
                        types[i] = object.getString("type");
                        time_languages[i] = object.getString("timeAndLanguage");
                        on_times[i] = object.getString("onTime");
                        actors[i] = object.getString("actors");
                        ids[i] = object.getString("id");
                        descriptions[i] = object.getString("description");
                        imgUrls[i] = object.getString("img");
//                        System.out.println(object.getString("img"));
//                        Bitmap bitmap = ImageService.getBitmapFromUrl(object.getString("img"));
//                        images[i] = bitmap;
                    }
                    System.out.println("Load movies complete...");
                } catch (SocketTimeoutException ee) {
                    ee.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                latch.countDown();
            }
        }).start();
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}
