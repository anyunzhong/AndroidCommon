package net.datafans.android.common.widget.controller;

import android.content.Context;
import android.support.v4.app.Fragment;

import net.datafans.android.common.widget.tabbar.TabbarFragment;

/**
 * Created by zhonganyun on 15/6/23.
 */
public abstract class TabbarController extends FragmentController {
    private  TabbarFragment fragment;
    @Override
    protected Fragment getRootFragment() {
        fragment = new TabbarFragment(this);
        return fragment;
    }


    public abstract String[] getTabItemNames();

    public abstract int[] getTabItemIcons();

    public abstract int getTabItemColor();

    public abstract Fragment getFragment(int index);

    public void setBadge(int index, int value, Context context){
        if (fragment == null) return;
        fragment.setBadge(index,value,context);
    }


}
