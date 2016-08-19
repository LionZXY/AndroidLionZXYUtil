package com.lionzxy.firstandroidapp.app.vk.music;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.lionzxy.firstandroidapp.app.MainActivity;
import com.lionzxy.firstandroidapp.app.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LionZXY on 19.08.2016.
 */
public class MusicCachedDownloader extends AsyncTask<MusicAdapter, MusicObject, List<MusicObject>> {
    private MusicAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public MusicCachedDownloader(@Nullable SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;

        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    protected List<MusicObject> doInBackground(MusicAdapter... params) {
        List<MusicObject> musicObjects = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = MainActivity.mDatabaseHelper.getReadableDatabase();
        adapter = params[0];
        Log.i("MusicCached", "Music download from cache");

        String query = "SELECT rowid, * FROM " + DatabaseHelper.DATABASE_TABLE;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    MusicObject musicObject = DatabaseHelper.Converter.getMusicObject(cursor);
                    musicObjects.add(musicObject);
                    publishProgress(musicObject);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        return musicObjects;
    }

    @Override
    protected void onProgressUpdate(MusicObject... values) {
        super.onProgressUpdate(values);
        adapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(List<MusicObject> musicObjects) {
        super.onPostExecute(musicObjects);

        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }
}
