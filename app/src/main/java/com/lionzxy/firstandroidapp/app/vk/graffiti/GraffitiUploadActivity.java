package com.lionzxy.firstandroidapp.app.vk.graffiti;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.lionzxy.firstandroidapp.app.R;
import com.lionzxy.firstandroidapp.app.generateip.activitys.BaseActivity;
import com.lionzxy.firstandroidapp.app.helpers.MultipartUtility;
import com.lionzxy.firstandroidapp.app.helpers.URIHelper;
import com.lionzxy.firstandroidapp.app.vk.video.VKLoginActivity;
import com.lionzxy.firstandroidapp.app.vk.video.VkVideo;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LionZXY on 22.07.2016.
 */
public class GraffitiUploadActivity extends BaseActivity {
    File image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graffity_upload);

        checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                image = new File(URIHelper.getPath(this, data.getData()));
                this.startActivityForResult(new Intent(this, VKLoginActivity.class), 2);
            }
        } else if (requestCode == 2) {
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("type", "graffiti");
                        try {
                            MultipartUtility multipartUtility = MultipartUtility.getURLAndUpload("docs.getUploadServer", hashMap, data.getStringExtra("token"));
                            multipartUtility.addFilePart("file", image);
                            final JSONObject jsonObject = new JSONObject(multipartUtility.finish().toString());
                            HashMap<String, String> hashMap1 = new HashMap<String, String>();
                            hashMap1.put("file", jsonObject.getString("file"));
                            JSONObject jsonObject1 = VkVideo.getAnswerWithThrow("docs.save", hashMap1, data.getStringExtra("token"));
                            jsonObject1 = jsonObject1.getJSONArray("response").getJSONObject(0);
                            String asMedia = "doc" + jsonObject1.get("owner_id") + "_" + jsonObject1.get("id");
                            hashMap1.clear();
                            hashMap1.put("user_id", jsonObject1.get("owner_id").toString());
                            hashMap1.put("attachment", asMedia);
                            VkVideo.getAnswerWithThrow("messages.send", hashMap1, data.getStringExtra("token"));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getBaseContext(), "Графити успешно отправленно. Смотрите в недавних сообщениях", Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (Exception e) {
                Toast.makeText(GraffitiUploadActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }

    public void onUpload(View v) {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    public void checkPermission(String... perm) {
        List<String> requestPerm = new ArrayList<String>();
        for (String p : perm)
            if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED && !ActivityCompat.shouldShowRequestPermissionRationale(this, p))
                requestPerm.add(p);
        if (requestPerm.size() > 0)
            ActivityCompat.requestPermissions(this, requestPerm.toArray(new String[requestPerm.size()]), 1);
    }
}
