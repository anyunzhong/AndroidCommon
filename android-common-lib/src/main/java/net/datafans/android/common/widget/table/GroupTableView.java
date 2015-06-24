package net.datafans.android.common.widget.table;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;

import net.datafans.android.common.widget.table.refresh.RefreshControlType;

public class GroupTableView<T> extends TableView<T> {

    public GroupTableView(Context context, RefreshControlType type,
                          boolean enableRefresh, boolean enableLoadMore,
                          boolean enableAutoLoadMore) {
        super(context, type, enableRefresh, enableLoadMore, enableAutoLoadMore);

        getView().setBackgroundColor(Color.GRAY);
        getAdapter().getListView().setDivider(new ColorDrawable(Color.WHITE));
        getAdapter().getListView().setDividerHeight(1);
    }


    @Override
    protected void setCellStyle(TableViewCell cell, int position) {
        super.setCellStyle(cell,position);

        if (position == 0) {
            cell.getView().setBackgroundColor(Color.RED);
        }
    }
}
