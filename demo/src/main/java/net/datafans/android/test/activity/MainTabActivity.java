package net.datafans.android.test.activity;

import android.os.Bundle;
import android.view.Menu;

import net.datafans.android.common.widget.controller.TabbarController;
import net.datafans.android.test.R;

public class MainTabActivity extends TabbarController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_tab, menu);
        return true;
    }


    @Override
    protected String getNavTitle() {
        return "微信";
    }


    @Override
    public String[] getTabItemNames() {
        return new String[]{ "微信", "通讯录", "发现", "我" };
    }

    @Override
    public int[] getTabItemIcons() {
        return new int[]{ R.drawable.maintab_1_selector, R.drawable.maintab_2_selector, R.drawable.maintab_3_selector,
                R.drawable.maintab_4_selector };
    }

    @Override
    public int getTabItemColor() {
        return R.color.tab_main_selector;
    }

    @Override
    public android.support.v4.app.Fragment getFragment(int index) {
        return new MessageFragment();
    }
}
