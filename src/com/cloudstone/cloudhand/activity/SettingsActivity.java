package com.cloudstone.cloudhand.activity;


import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

import com.cloudstone.cloudhand.R;

public class SettingsActivity extends PreferenceActivity {
	private EditTextPreference e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_settings);
        e = (EditTextPreference)findPreference("edittext_preference");
        e.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                //这里老是取得它上一个值
                e.setSummary(e.getText());
                return true;
            }
        });
    }
    
}
