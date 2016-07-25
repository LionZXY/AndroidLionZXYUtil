package com.lionzxy.firstandroidapp.app.vk.video;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lionzxy.firstandroidapp.app.R;
import com.lionzxy.firstandroidapp.app.generateip.activitys.BaseActivity;
import com.lionzxy.firstandroidapp.app.helpers.LoginPasswordAuth;

/**
 * Created by LionZXY on 22.07.2016.
 */
public class VKLoginActivity extends BaseActivity {
    public static final int NEW_TOKEN = 2;
    public static final int OLD_TOKEN = 4;
    Button lgnButton;
    EditText login, password, token;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If token also created
        if (getPreference().getString("token", null) != null && !(getIntent().hasExtra("state") && getIntent().getIntExtra("state", 0) == NEW_TOKEN)) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("token", getPreference().getString("token", null));
            setResult(OLD_TOKEN, returnIntent);
            finish();
        }
        this.setContentView(R.layout.vk_login_activity);

        lgnButton = (Button) findViewById(R.id.lgn_btn);
        login = (EditText) findViewById(R.id.editText_login);
        password = (EditText) findViewById(R.id.editText_password);
        token = (EditText) findViewById(R.id.editText_token);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        lgnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.getText().toString().length() > 0 && password.getText().toString().length() > 0) {
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                final String token = new LoginPasswordAuth(login.getText().toString(), password.getText().toString()).getAuthToken();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        getPreference().edit().putString("token", token).apply();
                                        Intent returnIntent = new Intent();
                                        returnIntent.putExtra("token", token);
                                        setResult(NEW_TOKEN, returnIntent);
                                        finish();
                                    }
                                });
                            } catch (final RuntimeException ex) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(VKLoginActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }).start();
                } else if (token.getText().toString().length() > 0) {
                    getPreference().edit().putString("token", token.getText().toString()).apply();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("token", token.getText().toString());
                    setResult(NEW_TOKEN, returnIntent);
                    finish();
                } else
                    Toast.makeText(VKLoginActivity.this, "Заполните необходимые поля", Toast.LENGTH_LONG).show();
            }
        });

    }

}
