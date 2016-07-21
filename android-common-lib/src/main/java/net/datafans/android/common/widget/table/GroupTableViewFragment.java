package net.datafans.android.common.widget.table;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.datafans.android.common.widget.table.refresh.RefreshControlType;

public abstract  class GroupTableViewFragment<T> extends PlainTableViewFragment<T> implements
		GroupTableViewDataSource<T> {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		TableView.Builder<T> builder = new GroupTableView.Builder<>();
		builder.setRefreshType(getRefreshControlType());
		builder.setEnableRefresh(enableRefresh());
		builder.setEnableLoadMore(enableLoadMore());
		builder.setEnableAutoLoadMore(enableAutoLoadMore());
		builder.setDataSource(this);
		builder.setDelegate(this);
		builder.setHeaderView(getTableHeaderView());
		builder.setFooterView(getTableFooterView());
		tableView = builder.build();
		return tableView.getView();
	}

	@Override
	public int getSections() {
		return 1;
	}

	@Override
	public int getSectionFooterHeight(int section) {
		return 1;
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
