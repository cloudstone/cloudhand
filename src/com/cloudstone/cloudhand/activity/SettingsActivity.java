package com.cloudstone.cloudhand.activity;


import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

import com.cloudstone.cloudhand.R;

/**
 * 
 * @author xhc
 *
 */
public class SettingsActivity extends PreferenceActivity {
    
    private EditTextPreference editText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_settings);
        editText = (EditTextPreference)findPreference("edittext_preference");
        
        editText.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                editText.setSummary(newValue.toString());
                return true;
            }
        });
    }
    
}
