package com.lionzxy.firstandroidapp.app.vk.music;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.lionzxy.firstandroidapp.app.R;
import com.lionzxy.firstandroidapp.app.generateip.activitys.BaseActivity;
import com.lionzxy.firstandroidapp.app.vk.video.VKLoginActivity;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MusicActivity extends BaseActivity {
    private MusicAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_list);

        this.startActivityForResult(new Intent(this, VKLoginActivity.class), 1);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        adapter = new MusicAdapter(new ArrayList<MusicObject>());
        recyclerView.setLayoutManager(new LinearLayoutManager(MusicActivity.this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            try {
                final String token = data.getStringExtra("token");

                new MusicDownloader(adapter, this).execute(token);

            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
