package com.lionzxy.firstandroidapp.app.vk.music.coverdownload;

import android.text.TextUtils;

import com.lionzxy.firstandroidapp.app.vk.music.MusicObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by LionZXY on 15.08.2016.
 */
public class SoundCloudDownloader {
    public static final String URLAPI = "https://api.soundcloud.com/tracks?client_id=ec6c85ac937282733972e914c8a3ed06&q=";

    private String searchRequst;

    public SoundCloudDownloader(String author, String name) {
        searchRequst = author + " - " + name;
    }

    public SoundCloudDownloader(MusicObject musicObject) {
        this(musicObject.getAuthorName(), musicObject.getMusicName());
    }


    public String findCover() {
        DataInputStream is = null;
        try {
            URL url = new URL(URLAPI + URLEncoder.encode(searchRequst, "UTF-8").replace("%20", "+"));
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Accept-Language", "ru");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "UTF-8");

            is = new DataInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            String aux = "";

            while ((aux = br.readLine()) != null) {
                builder.append(aux);
            }
            br.close();

            JSONArray result = new JSONArray(builder.toString());
            if (result != null && result.length() > 0) {
                JSONObject item = result.getJSONObject(0);
                if (item != null) {
                    if (item.has("artwork_url") && !TextUtils.isEmpty(item.getString("artwork_url")) && !item.getString("artwork_url").equals("null"))
                        return item.getString("artwork_url");
                    else if (item.has("user")) {
                        JSONObject jsonObject = item.getJSONObject("user");
                        if (jsonObject.has("avatar_url")
                                && !TextUtils.isEmpty(jsonObject.getString("avatar_url"))
                                && !jsonObject.getString("avatar_url").equals("null")
                                && !jsonObject.getString("avatar_url").equals("http://a1.sndcdn.com/images/default_avatar_large.png?1471271515"))
                            return jsonObject.getString("avatar_url");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }
}
