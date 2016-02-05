package net.datafans.android.test.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import net.datafans.android.test.R;


public class ViewPagerFragment extends Fragment {

    private LayoutInflater inflater;

    public ViewPagerFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.inflater = inflater;

        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        initView(view);


        return view;
    }


    private void initView(View view) {

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.fragment_tabmain_viewPager);
        ScrollIndicatorView indicator = (ScrollIndicatorView) view.findViewById(R.id.fragment_tabmain_indicator);

        indicator.setScrollBar(new ColorBar(getActivity(), getResources().getColor(R.color.colorPrimaryDark), 10));
        float unSelectSize = 20;
        float selectSize = unSelectSize * 1.0f;

        int selectColor = Color.WHITE;
        int unSelectColor = Color.LTGRAY;
        indicator.setOnTransitionListener(new OnTransitionTextListener(selectSize, unSelectSize, selectColor, unSelectColor));

        viewPager.setOffscreenPageLimit(4);

        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(indicator, viewPager);

        indicatorViewPager.setAdapter(new TabAdapter());
    }


    private class TabAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {
        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public View getViewForTab(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = inflater.inflate(R.layout.tab_top, viewGroup, false);
            }
            TextView textView = (TextView) view;
            textView.setText("tab" + i);
            return view;
        }

        @Override
        public View getViewForPage(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = inflater.inflate(R.layout.tab_content, viewGroup, false);
            }

            TextView textView = (TextView) view.findViewById(R.id.tab_content_textview);
            textView.setText("content" + i);
            return view;
        }
    }

}
