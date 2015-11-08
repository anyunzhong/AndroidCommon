package net.datafans.android.common.widget.controller;

import android.support.v4.app.Fragment;

import net.datafans.android.common.widget.table.GroupTableView;
import net.datafans.android.common.widget.table.GroupTableViewDataSource;
import net.datafans.android.common.widget.table.TableView;
import net.datafans.android.common.widget.table.TableViewDelegate;
import net.datafans.android.common.widget.table.TableViewFragment;

public abstract class GroupTableViewController<T> extends TableViewController<T> implements
        GroupTableViewDataSource<T>, TableViewDelegate {

    @Override
    protected Fragment getRootFragment() {

        if (tableView == null) {
            TableView.Builder<T> builder = new GroupTableView.Builder<>();
            builder.setContext(this);
            builder.setRefreshType(getRefreshControlType());
            builder.setEnableLoadMore(enableRefresh());
            builder.setEnableLoadMore(enableLoadMore());
            builder.setEnableAutoLoadMore(enableAutoLoadMore());
            builder.setDataSource(this);
            builder.setDelegate(this);
            builder.setHeaderView(getTableHeaderView());
            builder.setFooterView(getTableFooterView());
            tableView = builder.build();

        }
        return new TableViewFragment<>(tableView);
    }


    @Override
    public int getSections() {
        return 1;
    }

    @Override
    public int getSectionFooterHeight(int section) {
        return 150;
    }

    @Override
    public int getSectionHeaderHeight(int section) {
        return 150;
    }

    @Override
    public String getSectionFooterTitle(int section) {
        return "";
    }

    @Override
    public String getSectionHeaderTitle(int section) {
        return "";
    }

}
