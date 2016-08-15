package com.lionzxy.firstandroidapp.app.generateip.activitys;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lionzxy.firstandroidapp.app.R;
import com.lionzxy.firstandroidapp.app.generateip.brain.GenerateIP;
import com.lionzxy.firstandroidapp.app.generateip.enums.GenerateIpStep;

import java.util.ArrayList;
import java.util.List;

/**
 * com.lionzxy.firstandroidapp.app.activitys
 * Created by LionZXY on 16.01.2016.
 * FirstAndroidApp
 */
public class GenerateIpAcitivy extends BaseActivity implements View.OnClickListener {
    private TextView textView;
    private Button button;
    public final List<String> ips = new ArrayList<>();
    ArrayAdapter<String> aa;

    public GenerateIpStep step = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generateip_layout);

        textView = (TextView) findViewById(R.id.generateIpDialog);
        button = (Button) findViewById(R.id.generateIPButton);
        button.setOnClickListener(this);
        ListView listView = (ListView) findViewById(R.id.listView);

        aa = new ArrayAdapter<String>(this, R.layout.textlayout, R.id.textV, ips);
        listView.setAdapter(aa);
    }

    @Override
    public void onClick(View v) {
        if (step == null)
            step = GenerateIpStep.Unknow;
        if (v instanceof Button)
            switch (step) {
                case Unknow:
                    button.setText("Generating ip... Wait");
                    GenerateIP.generateIP(this);
                    step = GenerateIpStep.Generate;
                    return;

                case Generate:
                    Toast.makeText(this, "WAIT!!! Now ip is generating!", Toast.LENGTH_LONG).show();
                    return;

                case Finish:
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + textView.getText()));
                    startActivity(browserIntent);
                    finish();
                    step = GenerateIpStep.Unknow;
                    textView.setText(R.string.generateIPDialog);
                    button.setText(R.string.generateIPButton);
                    return;
            }
        else if (v instanceof TextView) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + ((TextView) v).getText()));
            startActivity(browserIntent);
        }
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setButtonText(String text) {
        button.setText(text);
    }

    public int getValue(String key, int defaultV) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        return SP.getInt(key, defaultV);
    }

    public void finish() {
        ips.add(textView.getText().toString());
        aa.notifyDataSetChanged();
    }
}
