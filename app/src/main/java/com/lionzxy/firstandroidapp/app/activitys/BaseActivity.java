package com.lionzxy.firstandroidapp.app.activitys;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.lionzxy.firstandroidapp.app.R;

/**
 * com.lionzxy.firstandroidapp.app.activitys
 * Created by LionZXY on 16.01.2016.
 * FirstAndroidApp
 */
public class BaseActivity extends Activity{
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSettings:{
                Intent i = new Intent(this, MyPreferenceActivity.class);
                startActivity(i);
                return true;
            }
        }
        Toast.makeText(this,"Not Found",Toast.LENGTH_LONG).show();
        return false;
    }
}
