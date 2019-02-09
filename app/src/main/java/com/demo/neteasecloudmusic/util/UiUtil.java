package com.demo.neteasecloudmusic.util;

import android.app.Activity;
import android.content.Context;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

/**
 * author:LeeCode
 * create:2019/2/8 20:29
 */
public class UiUtil {
    public static void initSlidr(Context context) {
        SlidrConfig config = new SlidrConfig.Builder()
                //滑动起始方向
                .position(SlidrPosition.LEFT)
                .edge(true)
                .edgeSize(0.18f)
                .build();
        Slidr.attach((Activity) context, config);
    }
}
