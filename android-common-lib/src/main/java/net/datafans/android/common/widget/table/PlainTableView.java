package net.datafans.android.common.widget.table;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import net.datafans.android.common.widget.table.refresh.RefreshControlType;

public class PlainTableView<T> extends TableView<T> {

    public PlainTableView(Context context, RefreshControlType type,
                          boolean enableRefresh, boolean enableLoadMore,
                          boolean enableAutoLoadMore, TableViewDataSource dataSource, TableViewDelegate delegate) {
        super(context, type, enableRefresh, enableLoadMore, enableAutoLoadMore, TableViewStyle.PLAIN, dataSource, delegate);

        //getView().setBackgroundColor(Color.WHITE);
 //       getAdapter().getListView().setDivider(null);
//        getAdapter().getListView().setHeaderDividersEnabled(false);
//        //禁止底部出现分割线
//        getAdapter().getListView().setFooterDividersEnabled(false);
        getAdapter().getListView().setDivider(new ColorDrawable(Color.rgb(216,216,216)));
        getAdapter().getListView().setDividerHeight(1);
    }

}
