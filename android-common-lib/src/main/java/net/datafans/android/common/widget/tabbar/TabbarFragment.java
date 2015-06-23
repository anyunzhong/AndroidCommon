package net.datafans.android.common.widget.tabbar;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;

import net.datafans.android.common.R;
import net.datafans.android.common.widget.controller.TabbarController;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabbarFragment extends Fragment {


    private IndicatorViewPager indicatorViewPager;

    private LayoutInflater inflater;

    private TabbarController controller;

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

}
