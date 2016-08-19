package com.lionzxy.firstandroidapp.app.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.lionzxy.firstandroidapp.app.vk.music.MusicObject;

import java.util.HashSet;

/**
 * Created by LionZXY on 19.08.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {
    // private String authorName, musicName, url, coverUrl = null;
    // public long duration, date, lyrics_id;
    // private MusicGenre musicGenre;
    public static final String MUSIC_ID = "music_id";
    public static final String AUTHOR_ID = "author_id";
    public static final String AUTHOR_NAME = "author_name";
    public static final String MUSIC_NAME = "music_name";
    public static final String URL = "url";
    public static final String COVER_URL = "coverUrl";
    public static final String DURATION = "duration";
    public static final String DATE = "date";
    public static final String LYRICS_ID = "lyrics_id";

    private static final String DATABASE_NAME = "lionzxy,utils.db";
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE = "musics";

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + DATABASE_TABLE + " (" +
            BaseColumns._ID + " integer primary key autoincrement, " +
            MUSIC_ID + " text not null, " +
            AUTHOR_ID + " text," +
            AUTHOR_NAME + " text," +
            MUSIC_NAME + " text," +
            URL + " text," +
            COVER_URL + " text," +
            DURATION + " integer," +
            DATE + " integer," +
            LYRICS_ID + " integer" +
            ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public HashSet<String> getStringHashSet(String table, String column) {
        HashSet<String> hashSet = new HashSet<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT rowid, " + column + " FROM " + DatabaseHelper.DATABASE_TABLE;
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    hashSet.add(cursor.getString(cursor.getColumnIndex(column)));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return hashSet;
    }

    public void dropTable(String table) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        try {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table);
            sqLiteDatabase.execSQL(DATABASE_CREATE_SCRIPT);
        } finally {
            sqLiteDatabase.close();
        }
    }

    public static class Converter {

        public static ContentValues getContentValues(MusicObject musicObject) {
            ContentValues values = new ContentValues();

            values.put(MUSIC_ID, musicObject.getMusicId());
            values.put(AUTHOR_ID, musicObject.getAuthorId());
            values.put(AUTHOR_NAME, musicObject.getAuthorName());
            values.put(MUSIC_NAME, musicObject.getMusicName());
            values.put(URL, musicObject.getURL());
            values.put(COVER_URL, musicObject.getCoverURL());
            values.put(DURATION, musicObject.getDuration());
            values.put(DATE, musicObject.getDate());
            values.put(LYRICS_ID, musicObject.getLyrics_id());

            return values;
        }

        public static MusicObject getMusicObject(Cursor cursor) {
            MusicObject musicObject = new MusicObject(
                    cursor.getString(cursor.getColumnIndex(MUSIC_ID)),
                    cursor.getString(cursor.getColumnIndex(AUTHOR_ID)));

            musicObject.setAuthorName(getString(cursor, AUTHOR_NAME));
            musicObject.setMusicName(getString(cursor, MUSIC_NAME));
            musicObject.setURL(getString(cursor, URL));
            musicObject.setCoverURL(getString(cursor, COVER_URL));
            musicObject.setDuration(getLong(cursor, DURATION));
            musicObject.setDate(getLong(cursor, DATE));
            musicObject.setLyricsId(getLong(cursor, LYRICS_ID));

            return musicObject;
        }

        public static String getString(Cursor cursor, String TAG) {
            return cursor.getString(cursor.getColumnIndex(TAG));
        }

        public static long getLong(Cursor cursor, String TAG) {
            return cursor.getLong(cursor.getColumnIndex(TAG));
        }
    }

}
