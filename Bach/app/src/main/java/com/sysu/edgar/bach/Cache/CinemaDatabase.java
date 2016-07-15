package com.sysu.edgar.bach.Cache;

import com.sysu.edgar.bach.Network.JsonParse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;

/**
 * Created by Edgar on 2016/7/14.
 */
public class CinemaDatabase {

    public String[] names;
    public String[] imgs;
    public String[] coordinates;
    public String[] locations;
    public String[] transports;
    public String[] movies;
    public String[] urls;
    public String[] cinemaIds;
    public String[] tels;
    public int length = 0;
    public int count = 0;
    private JSONArray jsonArray;

    public CinemaDatabase() {
        length = 0;
    }

    public CinemaDatabase(final String urlPath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Loading cinemas...");
                try {
                    jsonArray = JsonParse.getJsonArray(urlPath);
                    length = jsonArray.length();
                    names = new String[length];
                    imgs = new String[length];
                    coordinates = new String[length];
                    locations = new String[length];
                    transports = new String[length];
                    movies = new String[length];
                    urls = new String[length];
                    cinemaIds = new String[length];
                    tels = new String[length];

                    for (int i = 0; i < length; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        names[i] = object.getString("name");
                        imgs[i] = object.getString("img");
                        coordinates[i] = object.getString("coordinate");
                        locations[i] = object.getString("location");
                        transports[i] = object.getString("transport");
                        movies[i] = object.getString("movies");
                        urls[i] = object.getString("url");
                        cinemaIds[i] = object.getString("cinemaId");
                        tels[i] = object.getString("tel");
                        count++;
                    }
                    System.out.println("Load cinemas complete...");
                } catch (SocketTimeoutException e1) {
                    e1.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }).start();
    }

    public CinemaDatabase(CinemaDatabase other) {
        this.names = other.names;
        this.imgs = other.imgs;
        this.coordinates = other.coordinates;
        this.locations = other.locations;
        this.transports = other.transports;
        this.movies = other.movies;
        this.urls = other.urls;
        this.cinemaIds = other.cinemaIds;
        this.tels = other.tels;
        this.length = other.length;
        this.count = other.count;
    }

}
