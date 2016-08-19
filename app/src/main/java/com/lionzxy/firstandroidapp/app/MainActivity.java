package com.lionzxy.firstandroidapp.app;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lionzxy.firstandroidapp.app.generateip.activitys.BaseActivity;
import com.lionzxy.firstandroidapp.app.generateip.activitys.GenerateIpAcitivy;
import com.lionzxy.firstandroidapp.app.helpers.DatabaseHelper;
import com.lionzxy.firstandroidapp.app.vk.graffiti.GraffitiUploadActivity;
import com.lionzxy.firstandroidapp.app.vk.music.MusicActivity;
import com.lionzxy.firstandroidapp.app.vk.video.VideoActivity;


/**
 * com.lionzxy.firstandroidapp.app
 * Created by LionZXY on 14.01.2016.
 * FirstAndroidApp
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.mine_layout);

        mDatabaseHelper = new DatabaseHelper(this);

        ((Button) findViewById(R.id.main_generateIP)).setOnClickListener(this);
        ((Button) findViewById(R.id.main_generateLinkVK)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_generateIP: {
                Intent i = new Intent(this, GenerateIpAcitivy.class);
                startActivity(i);
                return;
            }
            case R.id.main_generateLinkVK: {
                Intent i = new Intent(this, VideoActivity.class);
                startActivity(i);
                return;
            }
            case R.id.main_uploadGraffiti: {
                Intent i = new Intent(this, GraffitiUploadActivity.class);
                startActivity(i);
                return;
            }
            case R.id.main_downloadMusic: {
                Intent i = new Intent(this, MusicActivity.class);
                startActivity(i);
                return;
            }
        }
    }

}
