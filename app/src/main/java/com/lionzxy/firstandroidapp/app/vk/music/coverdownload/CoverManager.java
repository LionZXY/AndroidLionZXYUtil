package com.lionzxy.firstandroidapp.app.vk.music.coverdownload;

import android.text.TextUtils;

import com.lionzxy.firstandroidapp.app.vk.music.MusicObject;

/**
 * Created by LionZXY on 15.08.2016.
 */
public class CoverManager {

    public static String findCover(MusicObject musicObject) {
        String cover = null;
        cover = new LastFmCoverDownloader(musicObject).findCover();
        if (TextUtils.isEmpty(cover)) cover = new YandexCoverDownloader(musicObject).findCover();
        if (TextUtils.isEmpty(cover)) cover = new SoundCloudDownloader(musicObject).findCover();
        return cover;
    }
}
