package com.lionzxy.firstandroidapp.app.vk.music.coverdownload;

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
 * Created by LionZXY on 13.08.2016.
 */
public class YandexCoverDownloader {
    public static final String URLAPI = "https://api.music.yandex.net/search?type=track&page=0&text=";

    private String searchRequst;

    public YandexCoverDownloader(String author, String name) {
        searchRequst = author + " - " + name;
    }

    public YandexCoverDownloader(MusicObject musicObject) {
        this(musicObject.getAuthorName(), musicObject.getMusicName());
    }


    public String findCover() {
        DataInputStream is = null;
        try {
            //URL url = new URL("https://api.music.yandex.net/search?type=track&page=0&text=%D0%98%D0%B7+%D0%BE%D0%BA%D0%BD%D0%B0");
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

            JSONObject answer = new JSONObject(builder.toString());
            if (answer != null) {
                JSONObject result = answer.getJSONObject("result");
                if (result != null && result.has("tracks")) {
                    JSONObject tracks = result.getJSONObject("tracks");
                    if (tracks != null) {
                        JSONArray results = tracks.getJSONArray("results");
                        if (results != null && results.length() > 0) {
                            JSONObject item = results.getJSONObject(0);
                            if (item.has("albums")) {
                                JSONArray albums = item.getJSONArray("albums");
                                String coverUri = albums.getJSONObject(0).getString("coverUri");
                                return "http://" + coverUri.substring(0, coverUri.indexOf('%')) + "200x200";
                            }
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
