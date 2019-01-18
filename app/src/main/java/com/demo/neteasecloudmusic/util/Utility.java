package com.demo.neteasecloudmusic.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.demo.neteasecloudmusic.gson.event.Event;
import com.demo.neteasecloudmusic.gson.event.Events;
import com.demo.neteasecloudmusic.gson.playList.UserPlayList;
import com.demo.neteasecloudmusic.gson.user.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utility {
    /**
     * 解析和处理服务器返回的数据
     */
//    public static UserMessage handleMessage(String response) {
//        if (!TextUtils.isEmpty(response)) {
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                JSONObject jsonObject1 = jsonObject.getJSONObject("profile");
//                String profile = jsonObject1.toString();
//                Log.d("Utility", "" + jsonObject1);
//                Log.d("Utility", "" + jsonObject1.get("nickname"));
////                JSONObject jsonObject2=jsonObject.getJSONObject("bindings");
//                return new Gson().fromJson(profile, UserMessage.class);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
    public static String handleShareUrl(String shareurl) {
        String userId = null;
        String pattern = "\\d+";
        Pattern pt = Pattern.compile(pattern);
        Matcher m = pt.matcher(shareurl);
        while (m.find()) {
            userId = m.group(0);
        }
        return userId;
    }

    public static User handleUserResponse(String response) {
        try {
            Log.d("Utility", response);
            JSONObject jsonObject = new JSONObject(response);
            String UserContent = jsonObject.toString();
            Log.d("Utility", "" + jsonObject + "\n" + UserContent);
            Log.d("Utility", "" + jsonObject.get("level"));
            return new Gson().fromJson(UserContent, User.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static UserPlayList handlePlayListResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String UserPlayListContent = jsonObject.toString();
            Log.d("Utility", "" + jsonObject.get("code"));
            Log.d("Utility", "" + jsonObject.get("playlist"));
            return new Gson().fromJson(UserPlayListContent, UserPlayList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static String handleProvince(String response, long provinceId) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsonObject1 = jsonObject.getJSONObject("86");
            String provinceName = jsonObject1.getString(String.valueOf(provinceId));
            Log.d("Utility", provinceName);
            return provinceName;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static String handleCity(String response, long provinceId, long cityId) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsonObject1 = jsonObject.getJSONObject(String.valueOf(provinceId));
            String cityName = jsonObject1.getString(String.valueOf(cityId));
            Log.d("Utility", cityName);
            return cityName;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Event handleEventList(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String content = jsonObject.toString();
                return new Gson().fromJson(content, Event.class);

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }


    public static String handleUnixTimeStamp(long unixTimeStamp) {
        if (unixTimeStamp != 0) {
            String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                    .format(new Date(unixTimeStamp));
            return date;
        }
        return null;
    }
}