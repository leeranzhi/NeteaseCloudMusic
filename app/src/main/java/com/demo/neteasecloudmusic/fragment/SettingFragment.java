package com.demo.neteasecloudmusic.fragment;


import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.demo.neteasecloudmusic.R;


public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
