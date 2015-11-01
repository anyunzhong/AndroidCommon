package net.datafans.android.test.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.datafans.android.common.widget.controller.TabbarController;
import net.datafans.android.test.R;

public class MainCusTabActivity extends TabbarController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onClickReturnButton() {
        setBadge(0, 5, this);
    }

    @Override
    protected boolean enableReturnButton() {
        return false;
    }


    @Override
    protected View getCusToolbarView() {

        LinearLayout view = new LinearLayout(this);
        TextView textView = new TextView(this);
        textView.setText("自定义");
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(20);
        view.addView(textView);
        return view;
    }

    @Override
    public boolean canScroll() {
        return false;
    }

    @Override
    protected String getNavTitle() {
        return "微信";
    }


    @Override
    public String[] getTabItemNames() {
        return new String[]{"微信", "通讯录", "", "发现", "我"};
    }

    @Override
    public int[] getTabItemIcons() {
        return new int[]{R.drawable.maintab_1_selector, R.drawable.maintab_2_selector, R.mipmap.tabbar_blank, R.drawable.maintab_3_selector,
                R.drawable.maintab_4_selector};
    }

    @Override
    public int getTabItemColor() {
        return R.color.tab_main_selector;
    }


    @Override
    public View getCusTabView(int index) {
        if (index == 2) {
            View view = getLayoutInflater().inflate(R.layout.cus_tab_view, null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            return view;
        }

        return super.getCusTabView(index);
    }

    @Override
    public android.support.v4.app.Fragment getFragment(int index) {
        return new MessageFragment();
    }
}
