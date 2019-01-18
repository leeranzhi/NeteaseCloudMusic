package com.demo.neteasecloudmusic.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.demo.neteasecloudmusic.R;
import com.demo.neteasecloudmusic.gson.user.User;
import com.demo.neteasecloudmusic.util.HttpUtil;
import com.demo.neteasecloudmusic.util.Utility;
import com.github.chrisbanes.photoview.PhotoView;
import com.parfoismeng.slidebacklib.SlideBack;
import com.parfoismeng.slidebacklib.callback.SlideBackCallBack;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button submit_button, reset_button;
    private EditText input_text;
    private TextView result_content;
    private PhotoView result_image;
    final String TAG = "MainActivity";
    String userId = null;
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        submit_button = (Button) findViewById(R.id.submit_button);
        reset_button = (Button) findViewById(R.id.reset_button);
        input_text = (EditText) findViewById(R.id.input_text);
        result_content = (TextView) findViewById(R.id.result_content);
        result_image = (PhotoView) findViewById(R.id.result_image);

        submit_button.setOnClickListener(this);
        reset_button.setOnClickListener(this);

        result_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                if (user != null) {
                    intent.putExtra("user_data", user);
                    startActivity(intent);
                }

            }
        });
        PreferenceManager.setDefaultValues(this,
                R.xml.preferences, false);
        Log.d(TAG, "此时活动onCreate!!!");
        handleIntent();

        SlideBack.register(this, new SlideBackCallBack() {
            @Override
            public void onSlideBack() {
                Toast.makeText(MainActivity.this, "退出", Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * 重写此方法，获取单一任务下的Intent传值更新操作
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "此时onNewIntent");
        setIntent(intent);
        handleIntent();
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

        //onDestroy记得解绑
        SlideBack.unregister(this);
    }

    /**
     * 解析Intent内容
     */
    private void handleIntent() {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        Uri uri = intent.getData();
        Log.d(TAG, "action" + action + "\n" + "type" + type + "\t" + "uri" + uri);
        if (Intent.ACTION_SEND.equals(action)) {
            if ("text/plain".equals(type)) {
                String shareText = intent.getStringExtra(Intent.EXTRA_TEXT);
                handleSendText(shareText);
            }

        } else if (Intent.ACTION_VIEW.equals(action)) {
            if (uri != null) {
                handleLink(intent, uri);
            }
        } else if (Intent.ACTION_PROCESS_TEXT.equals(action)) {
            CharSequence shareExtra = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
            Log.d(TAG, "查看一下传递过来的textExtra内容" + shareExtra);
            String shareText = shareExtra.toString();
            handleSendText(shareText);
        }

    }

    /**
     * 解析网页链接
     *
     * @param intent
     * @param uri
     */
    private void handleLink(Intent intent, Uri uri) {
        String host = uri.getHost();
        String dataString = intent.getDataString();
        Log.d(TAG, "" + host + "\n\n" + dataString);
        userId = Utility.handleShareUrl(dataString);
        Log.d(TAG, userId);
        parseMessage(userId);
        input_text.setText(dataString);
    }

    /**
     * 解析文本信息
     *
     * @param shareText
     */
    private void handleSendText(String shareText) {
        if (!TextUtils.isEmpty(shareText)) {
            //处理接收到的文本信息
            userId = Utility.handleShareUrl(shareText);
            parseMessage(userId);
            input_text.setText(shareText);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_button:
                String shareUrl = input_text.getText().toString();
                Log.d(TAG, "请看这里检查一下" + shareUrl);
                if (!TextUtils.isEmpty(shareUrl)) {
                    userId = Utility.handleShareUrl(shareUrl);
                    parseMessage(userId);
                } else {
                    Toast.makeText(MainActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.reset_button:
                input_text.setText("");
                break;
            default:
        }
    }

    private void parseMessage(String userId) {
        String userUrl = "http://47.94.88.105:3000/user/detail?uid=" + userId;
        HttpUtil.sendOkHttpRequest(userUrl,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "获取信息失败",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseText = response.body().string();
                        user = Utility.handleUserResponse(responseText);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (user != null) {
                                    Log.d(TAG, "检查一下此处情况" + user.profile.nickname);
                                    showImageView(user);
                                } else {
                                    Toast.makeText(MainActivity.this, "用户信息返回解析失败",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }

    private void showImageView(User user) {
        String userName = user.profile.nickname;
        String userImage = user.profile.avatarUrl;
        Log.d(TAG, "" + userName);
        result_content.setText(userName);
        Glide.with(this).load(userImage).into(result_image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Toast.makeText(this, "点击了设置", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, BaseActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }


    /**
     * 点击空白区域隐藏键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();       //得到当前页面的焦点，
            if (isShouldHideKeyBoard(v, ev)) { //判断用户点击的是否是空白区域
                hideKeyBoard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标比对，来判断是否隐藏键盘
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyBoard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],        //得到EditText在屏幕上下左右的坐标
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;


    }

    /**
     * 隐藏软键盘
     *
     * @param token
     */
    private void hideKeyBoard(IBinder token) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
