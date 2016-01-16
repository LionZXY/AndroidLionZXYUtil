package com.lionzxy.firstandroidapp.app.brain;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

/**
 * com.lionzxy.firstandroidapp.app.brain
 * Created by LionZXY on 16.01.2016.
 * FirstAndroidApp
 */
public class IntEditTextPreference extends EditTextPreference {

    public IntEditTextPreference(Context context) {
        super(context);
    }

    public IntEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IntEditTextPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected String getPersistedString(String defaultReturnValue) {
        return String.valueOf(getPersistedInt(-1));
    }

    @Override
    protected boolean persistString(String value) {
        return persistInt(Integer.valueOf(value));
    }
}
