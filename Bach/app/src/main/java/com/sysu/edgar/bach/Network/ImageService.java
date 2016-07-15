package com.sysu.edgar.bach.Network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Edgar on 2016/7/11.
 */
public class ImageService {

    private final static String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/Download/Bach/";

    public static Bitmap getBitmapFromUrl(String urlPath) throws Exception {
        byte[] data = getImage(urlPath);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        return bitmap;
    }

    private static byte[] getImage(String urlPath) throws Exception {
        URL url = new URL(urlPath);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(50 * 1000);
        InputStream inputStream = connection.getInputStream();
        byte[] data = readInputStream(inputStream);
        return data;
    }

    private static byte[] readInputStream(InputStream inputStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static void saveImage(Bitmap bitmap, String filename) throws Exception {
        File dir = new File(ALBUM_PATH);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File myFile = new File(ALBUM_PATH + filename);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myFile));
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        bos.flush();
        bos.close();
    }
}
