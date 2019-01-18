package com.demo.neteasecloudmusic.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.neteasecloudmusic.R;
import com.wang.avi.AVLoadingIndicatorView;

public class LoadActivity extends AppCompatActivity {

    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        avi = findViewById(R.id.loading_view2);
//        show();

    }

    private void show() {
        avi.show();
    }
}
