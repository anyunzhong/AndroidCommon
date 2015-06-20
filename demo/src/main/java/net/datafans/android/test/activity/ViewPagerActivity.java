package net.datafans.android.test.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import net.datafans.android.test.R;

public class ViewPagerActivity extends Activity {

    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        inflater = LayoutInflater.from(this);

        initView();

    }


    private void initView() {

        ViewPager viewPager = (ViewPager) findViewById(R.id.fragment_tabmain_viewPager);
        FixedIndicatorView indicator = (FixedIndicatorView) findViewById(R.id.fragment_tabmain_indicator);

        indicator.setScrollBar(new ColorBar(this, Color.RED, 10));
        float unSelectSize = 20;
        float selectSize = unSelectSize * 1.0f;

        int selectColor = Color.RED;
        int unSelectColor = Color.GRAY;
        indicator.setOnTransitionListener(new OnTransitionTextListener(selectSize, unSelectSize, selectColor, unSelectColor));

        viewPager.setOffscreenPageLimit(4);

        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);

        indicatorViewPager.setAdapter(new TabAdapter());
    }


    private class TabAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {
        @Override
        public int getCount() {
            return 4;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_pager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
