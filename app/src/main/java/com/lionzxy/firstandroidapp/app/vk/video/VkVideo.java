package com.lionzxy.firstandroidapp.app.vk.video;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Никита on 08.05.2016.
 */
public class VkVideo {
    static interface VKVideoR {
        void rVideo(HashMap<Resolution, String> resolutions, String title);
    }

    String videoId;

    public VkVideo(String url, final VKVideoR vkVideoR, final String token) {
        videoId = url.substring(url.indexOf("video") + 5);

        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("videos", videoId);
                try {
                    JSONObject jsonObject = getAnswerWithThrow("video.get", params, token).getJSONObject("response").getJSONArray("items").getJSONObject(0);
                    String title = jsonObject.getString("title");
                    JSONObject files = jsonObject.getJSONObject("files");
                    Iterator<String> keys = files.keys();

                    HashMap<Resolution, String> resolutionStringHashMap = new HashMap<Resolution, String>();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        resolutionStringHashMap.put(Resolution.getResolution(key), files.getString(key));
                    }

                    vkVideoR.rVideo(resolutionStringHashMap, title);
                } catch (Exception e) {
                    vkVideoR.rVideo(null, e.getMessage());
                }
            }
        }).start();
    }

    public static JSONObject getAnswerWithThrow(String method, HashMap<String, String> params, String token) throws Exception {
        URL url;

        url = new URL("https://api.vk.com/method/" + method + "?access_token=" + token + "&v=5.52");


        StringBuilder postData = new StringBuilder();
        if (params != null)
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", "UTF-8");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataBytes.length));
        DataOutputStream wr;
        wr = openConn(conn);
        wr.write(postDataBytes);
        wr.flush();
        wr.close();


        DataInputStream is = new DataInputStream(conn.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String answer = br.readLine();
        JSONObject exit = new JSONObject(answer);
        br.close();
        return exit;
    }

    public static DataOutputStream openConn(HttpURLConnection conn) throws Exception {
        DataOutputStream os = null;
        int timeout = 1000;
        while (os == null) {
            conn.setConnectTimeout(timeout);
            os = new DataOutputStream(conn.getOutputStream());
            timeout = timeout * 2;
        }
        return os;
    }
}
