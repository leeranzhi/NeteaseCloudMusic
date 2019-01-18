package com.demo.neteasecloudmusic.ui;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.demo.neteasecloudmusic.R;
import com.demo.neteasecloudmusic.fragment.SettingFragment;
import com.demo.neteasecloudmusic.ui.UserActivity;
import com.demo.neteasecloudmusic.util.UpdateUtil;

public class BaseActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String LOAD_SETTING = "load_onlyWifi";
    public static final String SAVE_SETTING = "save_record";
    public static final String THEME_SETTING = "dark_theme";
    private String TAG = "BaseActivity";
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initToolbar();
        replaceFragment(R.id.content_frame, new SettingFragment());
        try {
            UpdateUtil.getVersionName(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserActivity.initSlidr(this);

    }

    private void replaceFragment(int viewId, SettingFragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(viewId, fragment)
                .commit();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_base);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "此时活动onResume!!!!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "此时活动onPause！！！");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "此时活动onStart!!!!");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "此时活动onRestart！！");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "此时活动onDestroy!!!");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
//        if(key.equals(LOAD_SETTING)){
//            Preference conectPref=findPreference(key);
//
//            conectPref.setSummary(sharedPreferences.getBoolean(key,""));
//
//
//        }

    }

}
