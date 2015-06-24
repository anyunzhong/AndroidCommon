package net.datafans.android.common.widget.table;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import net.datafans.android.common.widget.table.refresh.RefreshControlType;

public class PlainTableView<T> extends TableView<T> {

    public PlainTableView(Context context, RefreshControlType type,
                          boolean enableRefresh, boolean enableLoadMore,
                          boolean enableAutoLoadMore) {
        super(context, type, enableRefresh, enableLoadMore, enableAutoLoadMore);

        getView().setBackgroundColor(Color.WHITE);
        getAdapter().getListView().setDivider(new ColorDrawable(Color.LTGRAY));
        getAdapter().getListView().setDividerHeight(1);
    }

}
