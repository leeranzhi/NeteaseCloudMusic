package com.demo.neteasecloudmusic.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.demo.neteasecloudmusic.fragment.OneFragment;
import com.demo.neteasecloudmusic.fragment.ThreeFragment;
import com.demo.neteasecloudmusic.fragment.TwoFragment;


public class MyAdapter extends FragmentPagerAdapter {
    //    List<OneFragment> oneFragmentList;
    int userId;

    public MyAdapter(FragmentManager fm, int userId) {
        super(fm);
        this.userId = userId;
    }

//    public MyAdapter(FragmentManager fm, List<OneFragment> fragmentsList) {
//        super(fm);
//        oneFragmentList = fragmentsList;
//    }

    @Override
    public Fragment getItem(int position) {
        Log.d("MyAdapter查看!!!!", "" + position);
//        return oneFragmentList.get(position);
        switch (position) {
            case 0:
                return OneFragment.newInstance(userId);
            case 1:
                return ThreeFragment.newInstance(userId,null);
            case 2:
                return TwoFragment.newInstance(userId);
            default:

        }
        return null;

    }

    @Override
    public int getCount() {
        return 3;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return "动态";
//    }

}
