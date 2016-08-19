package com.lionzxy.firstandroidapp.app.vk.music;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.lionzxy.firstandroidapp.app.R;
import com.lionzxy.firstandroidapp.app.generateip.activitys.BaseActivity;
import com.lionzxy.firstandroidapp.app.vk.video.VKLoginActivity;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MusicActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private MusicAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_list);

        mSwipeRefreshLayout = ((SwipeRefreshLayout) findViewById(R.id.swipeRefresh));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        adapter = new MusicAdapter(new ArrayList<MusicObject>());
        recyclerView.setLayoutManager(new LinearLayoutManager(MusicActivity.this));
        recyclerView.setAdapter(adapter);

        new MusicCachedDownloader(mSwipeRefreshLayout).execute(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            try {
                final String token = data.getStringExtra("token");

                new MusicDownloader(adapter, this, mSwipeRefreshLayout).execute(token);

            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRefresh() {
        this.startActivityForResult(new Intent(this, VKLoginActivity.class), 1);
    }
}
