package com.sysu.edgar.bach.Network;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Edgar on 2016/7/11.
 */
public class JsonParse {
    /**
     * 解析Json数据
     *
     * @param urlPath
     * @return mlists
     * @throws Exception
     */
    public static JSONArray getJsonArray(String urlPath) throws Exception {
        byte[] data = readParse(urlPath);
        JSONArray array = null;
        try {
            array = new JSONArray(new String(data));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    /**
     * 从指定的url中获取字节数组
     *
     * @param urlPath
     * @return 字节数组
     * @throws Exception
     */
    private static byte[] readParse(String urlPath) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(50 * 1000);
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        outStream.close();
        return outStream.toByteArray();
    }
}