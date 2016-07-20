package net.datafans.android.common.widget.table;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.table.refresh.RefreshControlType;

public abstract  class PlainTableViewFragment<T> extends Fragment implements
		TableViewDataSource<T>, TableViewDelegate {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		TableView.Builder<T> builder = new PlainTableView.Builder<>();
		builder.setRefreshType(getRefreshControlType());
		builder.setEnableLoadMore(enableRefresh());
		builder.setEnableLoadMore(enableLoadMore());
		builder.setEnableAutoLoadMore(enableAutoLoadMore());
		builder.setDataSource(this);
		builder.setDelegate(this);
		builder.setHeaderView(getTableHeaderView());
		builder.setFooterView(getTableFooterView());
		TableView tableView = builder.build();

		return tableView.getView();
	}


	protected RefreshControlType getRefreshControlType() {
		return RefreshControlType.None;
	}

	protected boolean enableRefresh() {
		return true;
	}

	protected boolean enableLoadMore() {
		return true;
	}

	protected boolean enableAutoLoadMore() {
		return false;
	}

	@Override
	public int getItemViewType(int section, int row) {
		return 0;
	}

	@Override
	public int getItemViewTypeCount() {
		return 1;
	}



	protected View getTableHeaderView() {
		return null;
	}

	protected View getTableFooterView() {
		return null;
	}

	@Override
	public int getRows(int i) {
		return 1;
	}

	@Override
	public void onClickRow(int section, int row) {
		LogHelper.info("click section: " + section + "  row: " + row);
	}

	@Override
	public void onRefresh() {
		LogHelper.info("refresh");
	}

	@Override
	public void onLoadMore() {
		LogHelper.info("load more");
	}
}
