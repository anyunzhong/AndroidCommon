package net.datafans.android.common.widget.tabbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;

import net.datafans.android.common.R;
import net.datafans.android.common.widget.badge.BadgeView;
import net.datafans.android.common.widget.controller.TabbarController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabbarFragment extends Fragment {


    private IndicatorViewPager indicatorViewPager;

    private LayoutInflater inflater;

    private TabbarController controller;

    private Map<Integer, View> badgeMaskMap = new HashMap<>();
    private Map<Integer, View> tipMap = new HashMap<>();
    private Map<Integer, BadgeView> iconBadgeMap = new HashMap<>();


    public TabbarFragment(TabbarController controller) {
        this.controller = controller;
    }

    public TabbarFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.inflater = inflater;

        View view = inflater.inflate(R.layout.fragment_tabbar, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.tabbar_viewPager);
        Indicator indicator = (Indicator) view.findViewById(R.id.tabbar_indicator);

        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new TabbarAdapter(getFragmentManager()));

        //viewPager.setCanScroll(true);
        viewPager.setOffscreenPageLimit(controller.getTabItemNames().length);
        // viewPager.setPrepareNumber(0);


    }


    private class TabbarAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        public TabbarAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return controller.getTabItemNames().length;
        }

        @Override
        public View getViewForTab(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = inflater.inflate(R.layout.tabbar_item, viewGroup, false);
            }
            ImageView icon = (ImageView) view.findViewById(R.id.tab_item_icon);
            icon.setImageDrawable(getResources().getDrawable(controller.getTabItemIcons()[i]));


            View mask = view.findViewById(R.id.mask);
            badgeMaskMap.put(i, mask);


            View tip = view.findViewById(R.id.tab_item_tip);
            tipMap.put(i, tip);

            TextView textView = (TextView) view.findViewById(R.id.tab_item_text);
            textView.setText(controller.getTabItemNames()[i]);
            Resources resource = getActivity().getResources();
            ColorStateList csl = resource.getColorStateList(controller.getTabItemColor());
            if (csl != null) {
                textView.setTextColor(csl);
            }

            return view;
        }

        @Override
        public Fragment getFragmentForPage(int index) {

            return controller.getFragment(index);
        }


    }

    public void showTip(int index, boolean show) {
        View tip = tipMap.get(index);
        if (show) {
            tip.setVisibility(View.VISIBLE);
        } else {
            tip.setVisibility(View.GONE);
        }
    }


    public void setBadge(int index, int value, Context context) {

        if (index >= controller.getTabItemNames().length) return;

        BadgeView badge = iconBadgeMap.get(index);
        View mask = badgeMaskMap.get(index);

        if (badge == null) {
            badge = new BadgeView(context, mask);
            badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
            badge.setTextColor(Color.WHITE);
            badge.setBadgeBackgroundColor(Color.RED);
            badge.setTextSize(12);
            badge.setBadgeMargin(65, 20);

            iconBadgeMap.put(index, badge);


        }
        badge.setText(String.valueOf(value));
        if (value > 0)
            badge.show();
        else
            badge.hide();


    }

}
