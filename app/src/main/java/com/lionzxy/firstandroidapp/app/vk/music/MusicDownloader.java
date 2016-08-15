package com.lionzxy.firstandroidapp.app.vk.music;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.lionzxy.firstandroidapp.app.R;
import com.lionzxy.firstandroidapp.app.vk.music.coverdownload.CoverManager;
import com.lionzxy.firstandroidapp.app.vk.video.VkVideo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LionZXY on 15.08.2016.
 */
public class MusicDownloader extends AsyncTask<String, MusicObject, List<MusicObject>> {
    private MusicAdapter musicAdapter;
    NotificationManager manager;
    NotificationCompat.Builder builder;

    public MusicDownloader(MusicAdapter adapter, Context context) {
        this.musicAdapter = adapter;
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Скачка списка")
                .setContentText("В прогрессе 0/0")
                .setSmallIcon(R.mipmap.ic_launcher);
        manager.notify(1, builder.build());
    }

    @Override
    protected List<MusicObject> doInBackground(String... params) {
        final List<MusicObject> lists = new ArrayList<>();
        try {
            HashMap<String, String> paramsVk = new HashMap<String, String>();
            paramsVk.put("need_user", "0");
            JSONObject jsonObject = VkVideo.getAnswerWithThrow("audio.get", paramsVk, params[0]);
            JSONArray musics = jsonObject.getJSONObject("response").getJSONArray("items");
            for (int i = 0; i < musics.length(); i++) {
                JSONObject music = musics.getJSONObject(i);
                MusicObject musicObject = new MusicObject(
                        String.valueOf(music.getLong("id")), String.valueOf(music.getLong("owner_id")));
                if (music.has("artist"))
                    musicObject.setAuthorName(music.getString("artist"));
                if (music.has("title"))
                    musicObject.setMusicName(music.getString("title"));
                if (music.has("url"))
                    musicObject.setURL(music.getString("url"));
                if (music.has("genre_id"))
                    musicObject.setMusicGenre(new MusicGenre(music.getInt("genre_id")));

                musicObject.setCoverURL(CoverManager.findCover(musicObject));


                builder.setContentText("В прогрессе " + i + "/" + musics.length()).setProgress(musics.length(), i, false);
                manager.notify(1, builder.build());
                publishProgress(musicObject);
                lists.add(musicObject);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lists;
    }

    @Override
    protected void onProgressUpdate(MusicObject... values) {
        super.onProgressUpdate(values);
        musicAdapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(List<MusicObject> musicObjects) {
        super.onPostExecute(musicObjects);
        manager.cancel(1);
        Log.i("MusicDown", "Done");
    }
}
