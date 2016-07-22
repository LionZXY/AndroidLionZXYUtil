package com.lionzxy.firstandroidapp.app.generateip.activitys;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.lionzxy.firstandroidapp.app.R;


/**
 * com.lionzxy.firstandroidapp.app.activitys
 * Created by LionZXY on 16.01.2016.
 * FirstAndroidApp
 */
public class MyPreferenceActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
