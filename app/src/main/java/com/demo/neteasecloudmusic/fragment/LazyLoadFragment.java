package com.demo.neteasecloudmusic.fragment;

import android.support.v4.app.Fragment;

public abstract class LazyLoadFragment extends Fragment {
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInVisible();
        }
    }

    protected void onInVisible() {
    }

    /**
     * 可见时调用
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见时调用
     */
    protected abstract void lazyLoad();
}
