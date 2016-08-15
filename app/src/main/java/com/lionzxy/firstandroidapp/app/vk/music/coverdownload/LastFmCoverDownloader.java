package com.lionzxy.firstandroidapp.app.vk.music.coverdownload;

import android.util.Log;

import com.lionzxy.firstandroidapp.app.vk.music.MusicObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by LionZXY on 15.08.2016.
 */
public class LastFmCoverDownloader {
    public static final String URLAPI = "http://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key=735f17fd9643aa52bbe0a56036ab5d66&format=json&artist=%artist%&track=%track%";
    String author, name;

    public LastFmCoverDownloader(String author, String name) {
        this.author = author;
        this.name = name;
    }

    public LastFmCoverDownloader(MusicObject musicObject) {
        this(musicObject.getAuthorName(), musicObject.getMusicName());
    }


    public String findCover() {
        DataInputStream is = null;
        try {
            URL url = new URL(URLAPI.replaceAll("%artist%", URLEncoder.encode(author, "UTF-8")).replaceAll("%track%", URLEncoder.encode(name, "UTF-8")).replace("%20", "+"));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

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
            JSONObject answer = new JSONObject(builder.toString());
            if (answer != null && answer.has("track")) {
                JSONObject track = answer.getJSONObject("track");
                if (track != null && track.has("album")) {
                    JSONObject album = track.getJSONObject("album");
                    if (album != null) {
                        JSONArray array = album.getJSONArray("image");
                        if (array != null && array.length() > 0)
                            for (int i = array.length() - 1; i > 0; i--) {
                                if (array.getJSONObject(i).has("#text"))
                                    return array.getJSONObject(i).getString("#text");
                            }
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
