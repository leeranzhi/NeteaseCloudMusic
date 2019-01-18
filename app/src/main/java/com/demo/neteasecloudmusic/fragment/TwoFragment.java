package com.demo.neteasecloudmusic.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.demo.neteasecloudmusic.R;
import com.demo.neteasecloudmusic.gson.user.Bindings;
import com.demo.neteasecloudmusic.gson.user.User;
import com.demo.neteasecloudmusic.util.HttpUtil;
import com.demo.neteasecloudmusic.util.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class TwoFragment extends LazyLoadFragment {

    private boolean isPrepared;
    View view;
    int userId;
    private LinearLayout userMessage;
    private TextView userLevel, userAge, userGender, userLocal, userDes;
    private ImageView userOther;
    private User user;
    private String TAG = "TwoFragment";
    private String provinceName;
    private String cityName;
    String weiBoUrl;
    private AVLoadingIndicatorView avi;

    public static TwoFragment newInstance(int userId) {
        TwoFragment fragment = new TwoFragment();
        Bundle args = new Bundle();
        args.putInt("userId", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt("userId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_two, container, false);
        avi = view.findViewById(R.id.loading_view2);
        userLevel = (TextView) view.findViewById(R.id.user_level);
        userAge = (TextView) view.findViewById(R.id.user_age);
        userGender = (TextView) view.findViewById(R.id.user_gender);
        userLocal = (TextView) view.findViewById(R.id.user_local);
        userDes = (TextView) view.findViewById(R.id.user_des);
        userOther = (ImageView) view.findViewById(R.id.user_other);
        userMessage = view.findViewById(R.id.userMessage);
        isPrepared = true;
        lazyLoad();
        userMessage.setVisibility(View.GONE);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        lazyLoad();
    }

    /**
     * 查询个人信息
     */
    private void queryPersonalMessage() {
        if (user == null) {
            avi.show();
            String url = "http://47.94.88.105:3000/user/detail?uid=" + userId;
            queryFromServer(url);
        } else {
            avi.hide();
            userMessage.setVisibility(View.VISIBLE);

            userLevel.setText("" + user.level);
            Log.d(TAG, "" + user.profile.gender);
            if (user.profile.gender == 1) {
                userGender.setText("性别：男");
            } else if (user.profile.gender == 2) {
                userGender.setText("性别：女");
            } else {
                userGender.setText("性别：未填");
            }

            String localResult = "地区：" + provinceName + " " + cityName;
            if (provinceName == null || cityName == null) {
                localResult = provinceName == null ? "地区：未知" : "地区：" + provinceName;

            }
            userLocal.setText(localResult);
            userDes.setText(user.profile.signature);
            for (Bindings bindings : user.bindingsList) {
                if (bindings.type == 2) {
                    weiBoUrl = bindings.url;
                    Log.d(TAG, weiBoUrl);
                    break;
                }
            }
            if (!TextUtils.isEmpty(weiBoUrl)) {
                Glide.with(getActivity()).load(R.mipmap.bind_weibo).into(userOther);
                userOther.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(weiBoUrl);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            } else {
                Glide.with(getActivity()).load(R.mipmap.unbind_weibo).into(userOther);
            }

            long UnixTimeStamp = OneFragment.ProvinceCityBirthday[2];
            Log.d(TAG, "打印出生日期的值" + UnixTimeStamp);
            if (UnixTimeStamp != 0) {
                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                        .format(new Date(UnixTimeStamp));
                userAge.setText("年龄：" + date);
            }

        }

    }

    /**
     * 从服务器上查询
     */
    private void queryFromServer(String url) {

        Log.d(TAG, url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "查询个人信息错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                user = Utility.handleUserResponse(responseText);
                String idUrl = "http://47.94.88.105/id_86.json";
                HttpUtil.sendOkHttpRequest(idUrl, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "查询省份错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String responseText = response.body().string();

                        provinceName = Utility.handleProvince(responseText, OneFragment.ProvinceCityBirthday[0]);
                        cityName = Utility.handleCity(responseText, OneFragment.ProvinceCityBirthday[0], OneFragment.ProvinceCityBirthday[1]);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                queryPersonalMessage();
                            }
                        });

                    }
                });
            }
        });

    }


    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        queryPersonalMessage();

    }
}
