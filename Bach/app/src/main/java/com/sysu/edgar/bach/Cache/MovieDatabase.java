package com.sysu.edgar.bach.Cache;

import android.graphics.Bitmap;
import android.os.Handler;

import com.sysu.edgar.bach.Network.ImageService;
import com.sysu.edgar.bach.Network.JsonParse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Edgar on 2016/7/13.
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
    public int count = 0;
    public int count_imgs = 0;
    private Handler handler_download = new Handler();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

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
        this.count = other.count;
    }

    public MovieDatabase() {
        length = 0;
    }

    public MovieDatabase(final String urlPath) {

        Runnable thread_1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Loading movies...");
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
                        count++;
                    }
                    System.out.println("Load movies complete...");
                } catch (SocketTimeoutException ee) {
                    ee.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NullPointerException e2) {
                    e2.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        };

        Runnable thread_2 = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("!!!!!!!!!Count: " + count + " Length: " + length);
                    if (count == length && length != 0) {
                        System.out.println("Downloading images...");
                        for (int i = 0; i < count; i++) {
                            try {
                                Bitmap bitmap = ImageService.getBitmapFromUrl(imgUrls[i]);
                                ImageService.saveImage(bitmap, ids[i] + ".png");
                                count_imgs++;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("Download images complete...");
                    } else {
                        handler_download.postDelayed(this, 5 * 1000);
                    }
                } catch (NullPointerException eee) {
                    eee.printStackTrace();
                }
            }
        };

        executorService.execute(thread_1);
        executorService.execute(thread_2);

        executorService.shutdown();

    }

}
