package com.cloudstone.cloudhand.activity;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

import com.cloudstone.cloudhand.R;
import com.cloudstone.cloudhand.logic.MiscLogic;

/**
 * 
 * @author xhc
 *
 */
public class SettingsActivity extends PreferenceActivity {
    
    private EditTextPreference editText;
    private CheckBoxPreference checkBox;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO deprecated
        addPreferencesFromResource(R.xml.preference_settings);
        editText = (EditTextPreference)findPreference("edittext_preference");
        checkBox = (CheckBoxPreference)findPreference("checkbox_noNet");
        
        editText.setSummary(MiscLogic.getInstance().getServerIP());
        checkBox.setChecked(MiscLogic.getInstance().getNoNet());
        
        editText.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                editText.setSummary(newValue.toString());
                MiscLogic.getInstance().saveServerIP(newValue.toString());
                return true;
            }
        });
        
        checkBox.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {  
            
            @Override  
            public boolean onPreferenceChange(Preference preference, Object newValue) { 
                checkBox.setChecked((Boolean)newValue);
                MiscLogic.getInstance().saveNoNet((Boolean)newValue);
                return true;  
            }  
        });  
    }
    
}
