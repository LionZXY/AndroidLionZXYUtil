package com.lionzxy.firstandroidapp.app.generateip.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lionzxy.firstandroidapp.app.R;

/**
 * com.lionzxy.firstandroidapp.app.activitys
 * Created by LionZXY on 16.01.2016.
 * FirstAndroidApp
 */
public class BaseActivity extends Activity {
    public static String PACKAGE_NAME = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSettings: {
                Intent i = new Intent(this, MyPreferenceActivity.class);
                startActivity(i);
                return true;
            }
            case R.id.menuExitVk: {
                if (getPreference().getString("token", null) == null) {
                    Toast.makeText(this, "Активного токена не обнаружено", Toast.LENGTH_LONG).show();
                } else {
                    getPreference().edit().remove("token").apply();
                    Toast.makeText(this, "Успешно", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        }
        Toast.makeText(this, "Not Found", Toast.LENGTH_LONG).show();
        return false;
    }

    public SharedPreferences getPreference() {
        return getSharedPreferences(PACKAGE_NAME == null ? PACKAGE_NAME = getApplicationContext().getPackageName() : PACKAGE_NAME, MODE_PRIVATE);
    }
}
