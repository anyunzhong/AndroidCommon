package net.datafans.android.common.widget.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import net.datafans.android.common.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhonganyun on 16/2/11.
 */
public class PageIndicator extends LinearLayout {

    private List<View> dotViews = new ArrayList<>();
    private int lastSelected = 0;

    public PageIndicator(Context context) {
        super(context);
        init();
    }

    public PageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {

        setOrientation(HORIZONTAL);
        for (int i = 0; i < 5; i++) {
            createDotView();
        }
    }

    private void createDotView() {
        View dot = new View(getContext());
        dot.setBackgroundResource(R.drawable.dot_light);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
        params.rightMargin = 15;
        addView(dot, params);
        dotViews.add(dot);
    }

    public void setPageCount(int count) {
        if (count == 1) count = 0;
        if (count > dotViews.size()) {

            for (int i = dotViews.size(); i < count; i++) {
                createDotView();
            }

            for (int i = 0; i < dotViews.size(); i++) {
                View dotView = dotViews.get(i);
                dotView.setVisibility(VISIBLE);
            }

        } else if (count < dotViews.size()) {
            for (int i = 0; i < dotViews.size(); i++) {
                View dotView = dotViews.get(i);
                dotView.setVisibility(VISIBLE);
                if (i < count) continue;
                dotView.setVisibility(GONE);
            }
        }
    }

    public void setCurrentPage(int index) {
        if (index > dotViews.size() - 1) return;
        View last = dotViews.get(lastSelected);
        last.setBackgroundResource(R.drawable.dot_light);

        View current = dotViews.get(index);
        current.setBackgroundResource(R.drawable.dot_dark);
        lastSelected = index;

    }

}
