package com.lionzxy.firstandroidapp.app.vk.video;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lionzxy.firstandroidapp.app.R;
import com.lionzxy.firstandroidapp.app.generateip.activitys.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Никита on 08.05.2016.
 */
public class VideoActivity extends BaseActivity implements View.OnClickListener, VkVideo.VKVideoR {
    Spinner spinner;
    EditText editText;
    Button openVideo, copyLink, generate;
    LinearLayout videoFrame;
    List<String> links = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.video_layout);

        spinner = (Spinner) findViewById(R.id.spinner);
        openVideo = (Button) findViewById(R.id.button2);
        editText = (EditText) findViewById(R.id.editText);
        generate = (Button) findViewById(R.id.button);
        videoFrame = (LinearLayout) findViewById(R.id.videoFrame);
        copyLink = (Button) findViewById(R.id.btn_copyLink);

        videoFrame.setVisibility(View.GONE);

        generate.setOnClickListener(this);
        openVideo.setOnClickListener(this);
        copyLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.button: {
                    VideoActivity.this.startActivityForResult(new Intent(VideoActivity.this, VKLoginActivity.class), v.getId());
                    break;
                }
                case R.id.button2: {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(links.get(spinner.getSelectedItemPosition())));
                    intent.setDataAndType(Uri.parse(links.get(spinner.getSelectedItemPosition())), "video/mp4");
                    startActivity(intent);
                    break;
                }
                case R.id.btn_copyLink: {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Video Link", links.get(spinner.getSelectedItemPosition()));
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(VideoActivity.this, "Copy to clipboard", Toast.LENGTH_LONG).show();
                    break;
                }
            }
        } catch (Exception e) {
            Toast.makeText(VideoActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void rVideo(final HashMap<Resolution, String> resolutions, final String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (resolutions == null)
                    Toast.makeText(VideoActivity.this, "Exception: " + title, Toast.LENGTH_LONG).show();
                else {
                    ((TextView) findViewById(R.id.videoTitle)).setText(title);
                    Iterator<Resolution> resolutionIterator = resolutions.keySet().iterator();
                    List<String> resolutionsList = new ArrayList<String>();
                    while (resolutionIterator.hasNext()) {
                        Resolution resolution = resolutionIterator.next();
                        links.add(resolutions.get(resolution));
                        resolutionsList.add(resolution.getName());
                    }

                    videoFrame.setVisibility(View.VISIBLE);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(VideoActivity.this, android.R.layout.simple_spinner_item, resolutionsList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text", editText.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        editText.setText(outState.getString("text"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == R.id.button) {
            try {
                new VkVideo(editText.getText().toString(), this, data.getStringExtra("token"));
            } catch (Exception e) {
                Toast.makeText(VideoActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
