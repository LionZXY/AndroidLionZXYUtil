package com.lionzxy.firstandroidapp.app.vk.music;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by LionZXY on 12.08.2016.
 */
public class MusicObject {
    private final String musicId, authorId;
    private String authorName, musicName, url, coverUrl = null;
    public long duration, date, lyrics_id;
    private MusicGenre musicGenre;

    public MusicObject(String id, String authorId) {
        musicId = id;
        this.authorId = authorId;
    }

    public synchronized MusicObject setCoverURL(String coverUrl) {
        this.coverUrl = coverUrl;
        return this;
    }

    public String getCoverURL() {
        return coverUrl;
    }

    public MusicObject setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public MusicObject setMusicName(String musicName) {
        this.musicName = musicName;
        return this;
    }

    public MusicObject setMusicGenre(MusicGenre musicGenre) {
        this.musicGenre = musicGenre;
        return this;
    }

    public MusicObject setURL(String url) {
        this.url = url;
        return this;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getMusicName() {
        return musicName;
    }

    public String getURL() {
        return url;
    }

    public MusicGenre getMusicGenre() {
        return musicGenre;
    }

    public boolean checkCached() {
        File file = new File(Environment.getExternalStorageDirectory() + "/.vkontakte/cache/audio/", authorId + "_" + musicId);
        return file.exists();
    }

    public String getMusicId() {
        return musicId;
    }
}
