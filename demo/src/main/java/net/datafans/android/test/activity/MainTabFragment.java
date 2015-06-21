package net.datafans.android.test.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import net.datafans.android.test.R;

public class MainTabFragment extends Fragment {


    private IndicatorViewPager indicatorViewPager;

    private LayoutInflater inflater;

    public MainTabFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.inflater = inflater;

        View view = inflater.inflate(R.layout.fragment_main_tab, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.tabmain_viewPager);
        Indicator indicator = (Indicator) view.findViewById(R.id.tabmain_indicator);

        float unSelectSize = 11;
        float selectSize = unSelectSize * 1.0f;

        int selectColor = R.color.tab_main_text_1;
        int unSelectColor = R.color.tab_main_text_2;
        //indicator.setOnTransitionListener(new OnTransitionTextListener(selectSize, unSelectSize, selectColor, unSelectColor));



        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new TabMainAdapter());
        // 禁止viewpager的滑动事件
        //viewPager.setCanScroll(true);
        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(4);
        // 默认是1,，自动预加载左右两边的界面。设置viewpager预加载数为0。只加载加载当前界面。
       // viewPager.setPrepareNumber(0);


    }


    private class TabMainAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {


        private String[] tabNames = { "微信", "通讯录", "发现", "我" };
        private int[] tabIcons = { R.drawable.maintab_1_selector, R.drawable.maintab_2_selector, R.drawable.maintab_3_selector,
                R.drawable.maintab_4_selector };


        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public View getViewForTab(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = inflater.inflate(R.layout.tab_main, viewGroup, false);
            }
            ImageView icon = (ImageView) view.findViewById(R.id.tab_item_icon);
            icon.setImageDrawable(getResources().getDrawable(tabIcons[i]));

            TextView textView = (TextView) view.findViewById(R.id.tab_item_text);
            textView.setText(tabNames[i]);

            return view;
        }

        @Override
        public View getViewForPage(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = inflater.inflate(R.layout.tab_content, viewGroup, false);
            }

            TextView textView = (TextView) view.findViewById(R.id.tab_content_textview);
            textView.setText("content:" + tabNames[i]);
            return view;
        }
    }


}
