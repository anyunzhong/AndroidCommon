package net.datafans.android.common.widget.controller;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import net.datafans.android.common.widget.tabbar.TabbarFragment;

/**
 * Created by zhonganyun on 15/6/23.
 */
public abstract class TabbarController extends FragmentController {
    private TabbarFragment fragment;

    @Override
    protected Fragment getRootFragment() {
        fragment = new TabbarFragment(this);
        return fragment;
    }


    public abstract String[] getTabItemNames();

    public abstract int[] getTabItemIcons();

    public abstract int getTabItemColor();

    public abstract Fragment getFragment(int index);

    public View getCusTabView(int index) {
        return null;
    }

    public void setBadge(int index, int value, Context context) {
        if (fragment == null) return;
        fragment.setBadge(index, value, context);
    }

    public void showTip(int index, boolean show) {
        if (fragment == null) return;
        fragment.showTip(index, show);
    }

    public boolean canScroll() {
        return true;
    }

    public void onClickTab(int index){}



}
