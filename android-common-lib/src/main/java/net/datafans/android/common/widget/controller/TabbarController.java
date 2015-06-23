package net.datafans.android.common.widget.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import net.datafans.android.common.widget.tabbar.TabbarFragment;

/**
 * Created by zhonganyun on 15/6/23.
 */
public abstract class TabbarController extends SupportFragmentController {

    @Override
    protected Fragment getRootFragment() {
        TabbarFragment fragment = new TabbarFragment(this);
        return fragment;
    }


    public abstract String[] getTabItemNames();

    public abstract int[] getTabItemIcons();

    public abstract int getTabItemColor();

    public abstract Fragment getFragment(int index);

}
