package com.demo.neteasecloudmusic.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.demo.neteasecloudmusic.R;
import com.demo.neteasecloudmusic.adapter.MyAdapter;
import com.demo.neteasecloudmusic.fragment.OneFragment;
import com.demo.neteasecloudmusic.fragment.ThreeFragment;
import com.demo.neteasecloudmusic.gson.playList.PlayList;
import com.demo.neteasecloudmusic.gson.user.User;
import com.demo.neteasecloudmusic.gson.playList.UserPlayList;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity implements ViewPager
        .OnPageChangeListener, TabLayout.OnTabSelectedListener,ThreeFragment.OnFragmentInteractionListener {

    private String TAG = "UserActivity";
    private Intent intent;
    private CollapsingToolbarLayout collapsingToolbar;
    private User user;
    private ImageView userImageView;
    private TextView userContentText;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<OneFragment> fragmentList = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        setContentView(R.layout.activity_user);
        toolbar = (Toolbar) findViewById(R.id.user_toolbar);
        collapsingToolbar = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        userImageView = (ImageView) findViewById(R.id.user_image_view);
//        userContentText = (TextView) findViewById(R.id.user_text);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        loadUserImageView();
        //添加右滑返回功能
        initSlidr(this);
        initViewPager();

    }

    private void initViewPager() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);


        //注册监听
        viewPager.addOnPageChangeListener(this);
        tabLayout.addOnTabSelectedListener(this);

//        for(int i=0;i<3;i++){
//            OneFragment oneFragment=OneFragment.newInstance(user.profile.userId);
//            fragmentList.add(oneFragment);
//        }
//
//        myAdapter = new MyAdapter
//                (getSupportFragmentManager(),fragmentList);
        myAdapter = new MyAdapter
                (getSupportFragmentManager(), user.profile.userId);
        viewPager.setAdapter(myAdapter);
        viewPager.setCurrentItem(0);

//        tabLayout.setupWithViewPager(viewPager);
    }

    public static void initSlidr(Context context) {
        SlidrConfig config = new SlidrConfig.Builder()
                //滑动起始方向
                .position(SlidrPosition.LEFT)
                .edge(true)
                .edgeSize(0.18f)
                .build();
        Slidr.attach((Activity) context, config);
    }

    private void loadUserImageView() {
        intent = getIntent();
        user = (User) intent.getSerializableExtra("user_data");
        collapsingToolbar.setTitle(user.profile.nickname);
        Glide.with(this).
                load(user.profile.backgroundUrl).into(userImageView);
//        userContentText.setText(user.bindingsList.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Toast.makeText(this, "点击了分享", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, LoadActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 实现监听事件的方法
     *
     * @param tab
     */

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //TabLayout中的TabItem被选中时触发
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    /**
     * @param i
     * @param v
     * @param i1
     */

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        //viewPager滑动之后显示触发
        tabLayout.getTabAt(position).select();
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
