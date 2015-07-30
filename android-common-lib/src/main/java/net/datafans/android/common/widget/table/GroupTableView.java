package net.datafans.android.common.widget.table;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.datafans.android.common.widget.table.refresh.RefreshControlType;

public class GroupTableView<T> extends TableView<T> {

    public GroupTableView(Context context, RefreshControlType type,
                          boolean enableRefresh, boolean enableLoadMore,
                          boolean enableAutoLoadMore) {
        super(context, type, enableRefresh, enableLoadMore, enableAutoLoadMore, TableViewStyle.GROUP);

        getView().setBackgroundColor(Color.rgb(235,235,235));
        getAdapter().getListView().setDivider(null);
//       getAdapter().getListView().setDivider(new ColorDrawable(Color.rgb(216,216,216)));
//       getAdapter().getListView().setDividerHeight(1);

    }
}
