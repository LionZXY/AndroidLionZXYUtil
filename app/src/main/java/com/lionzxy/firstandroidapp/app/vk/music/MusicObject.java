package com.lionzxy.firstandroidapp.app.vk.music;

import android.os.Environment;

import java.io.File;

/**
 * Created by LionZXY on 12.08.2016.
 */
public class MusicObject {
    private long idInBd;
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

    public MusicObject setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public MusicObject setDate(long date) {
        this.date = date;
        return this;
    }

    public MusicObject setLyricsId(long lyrics_id) {
        this.lyrics_id = lyrics_id;
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

    public String getAuthorId() {
        return authorId;
    }

    public String getUrl() {
        return url;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public long getDuration() {
        return duration;
    }

    public long getDate() {
        return date;
    }

    public long getLyrics_id() {
        return lyrics_id;
    }

    public long getIdInBd() {
        return idInBd;
    }

    public void setIdInBd(long idInBd) {
        this.idInBd = idInBd;
    }



    public boolean checkCached() {
        File file = new File(Environment.getExternalStorageDirectory() + "/.vkontakte/cache/audio/", authorId + "_" + musicId);
        return file.exists();
    }

    public String getMusicId() {
        return musicId;
    }
}
