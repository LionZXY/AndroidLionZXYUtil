package com.lionzxy.firstandroidapp.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import com.lionzxy.firstandroidapp.app.activitys.BaseActivity;
import com.lionzxy.firstandroidapp.app.activitys.GenerateIpAcitivy;


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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_generateIP: {
                Intent i = new Intent(this, GenerateIpAcitivy.class);
                startActivity(i);
                return;
            }
        }
    }

}
