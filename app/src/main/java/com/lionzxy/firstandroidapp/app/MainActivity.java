package com.lionzxy.firstandroidapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lionzxy.firstandroidapp.app.generateip.activitys.BaseActivity;
import com.lionzxy.firstandroidapp.app.generateip.activitys.GenerateIpAcitivy;
import com.lionzxy.firstandroidapp.app.vkvideo.VideoActivity;


/**
 * com.lionzxy.firstandroidapp.app
 * Created by LionZXY on 14.01.2016.
 * FirstAndroidApp
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.mine_layout);

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
        }
    }

}
