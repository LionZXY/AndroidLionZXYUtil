package com.lionzxy.firstandroidapp.app.helpers;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by LionZXY on 22.07.2016.
 */
public class LoginPasswordAuth {
    private static final String TAG = "VKAuth";
    String login, paswd, authtoken = null;


    public LoginPasswordAuth(String loginPswd, char splitter) {
        String[] strings = loginPswd.split(splitter + "");
        this.login = strings[0];
        this.paswd = strings[1];
    }

    public LoginPasswordAuth(String login, String paswd) {
        this.login = login;
        this.paswd = paswd;
    }

    public String getAuthToken() throws RuntimeException {
        if (authtoken == null) {
            relogging();
        }
        return authtoken;
    }

    public void relogging() throws RuntimeException {
        Log.i(TAG, "Get authtoken...");
        StringBuilder sb = new StringBuilder();
        try {
            String url = "https://oauth.vk.com/token?grant_type=password&client_id=2274003&client_secret=hHbZxrka2uZ6jB1inYsH&" +
                    "username=" + login + "&password=" + paswd + "&scope=docs,messages,notify,friends,wall,groups,offline";
            URL urlObj;
            InputStream is = null;
            BufferedReader br;
            String line;
            urlObj = new URL(url);
            is = urlObj.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            JSONObject jsonObj = new JSONObject(sb.toString());
            authtoken = jsonObj.get("access_token").toString();
            if (is != null) is.close();
            br.close();
        } catch (Exception e) {
            if (sb.length() > 0)
                throw new RuntimeException(sb.toString());
            else throw new RuntimeException(e.getMessage());
        }
    }
}
