package net.datafans.android.common.widget.table;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import net.datafans.android.common.R;

import butterknife.ButterKnife;

public abstract class TableViewCell<T> {

    protected ViewGroup cell;

    protected RelativeLayout container;

    public  View divider;

    protected  RelativeLayout arrow;

    public TableViewCell(int layout, LayoutInflater flater) {


        container = new RelativeLayout(flater.getContext());
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        container.setLayoutParams(params);


        cell = (ViewGroup)flater.inflate(layout, null);
        RelativeLayout.LayoutParams cellParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cellParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        cellParams.bottomMargin = 0;
        cellParams.rightMargin = 0;

        container.addView(cell,cellParams);

        ButterKnife.inject(this, cell);



        divider = new View(flater.getContext());
        divider.setBackgroundColor(Color.LTGRAY);
        RelativeLayout.LayoutParams dividerParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dividerParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        dividerParams.height=1;
        dividerParams.leftMargin = 50;
        container.addView(divider,dividerParams);


        ImageView arrowImageView = new ImageView(flater.getContext());
        arrowImageView.setImageResource(R.drawable.arrow_right);

        arrow = new RelativeLayout(flater.getContext());
        RelativeLayout.LayoutParams arrowLp = new RelativeLayout.LayoutParams(40, 40);
        arrowLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        arrowLp.addRule(RelativeLayout.CENTER_VERTICAL);
        arrowLp.rightMargin = 20;
        container.addView(arrow,arrowLp);
        arrow.addView(arrowImageView);
        arrow.setVisibility(View.GONE);

    }

    protected ViewGroup getView() {
        return container;
    }

    protected abstract void refresh(T t);

}
