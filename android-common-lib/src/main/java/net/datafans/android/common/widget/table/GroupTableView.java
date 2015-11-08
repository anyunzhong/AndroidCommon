package net.datafans.android.common.widget.table;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import net.datafans.android.common.widget.table.refresh.RefreshControlType;

public class GroupTableView<T> extends TableView<T> {

    protected GroupTableView(Context context, RefreshControlType type,
                             boolean enableRefresh, boolean enableLoadMore,
                             boolean enableAutoLoadMore, TableViewDataSource<T> dataSource, TableViewDelegate delegate,View headerView, View footerView) {
        super(context, type, enableRefresh, enableLoadMore, enableAutoLoadMore, TableViewStyle.GROUP, dataSource, delegate, headerView, footerView);

        getView().setBackgroundColor(Color.rgb(235, 235, 235));
        getAdapter().getListView().setDivider(null);

    }

    public static class Builder<T> extends TableView.Builder<T> {

        public GroupTableView<T> build() {

            return new GroupTableView<>(getContext(), getRefreshType(), isEnableRefresh(), isEnableLoadMore(), isEnableAutoLoadMore(), getDataSource(), getDelegate(), getHeaderView(), getFooterView());

        }
    }
}
