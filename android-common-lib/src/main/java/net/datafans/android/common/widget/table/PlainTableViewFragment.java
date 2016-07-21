package net.datafans.android.common.widget.table;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.datafans.android.common.data.service.BaseResponse;
import net.datafans.android.common.data.service.DataService;
import net.datafans.android.common.data.service.DataServiceDelegate;
import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.table.refresh.RefreshControlType;

public abstract  class PlainTableViewFragment<T> extends Fragment implements
		TableViewDataSource<T>, TableViewDelegate, DataServiceDelegate {

	protected TableView tableView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		TableView.Builder<T> builder = new PlainTableView.Builder<>();
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


	protected RefreshControlType getRefreshControlType() {
		return RefreshControlType.BGANormal;
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


	@Override
	public void onStatusOk(BaseResponse response, DataService service) {
		onEnd();
	}

	@Override
	public void onStatusError(BaseResponse response, DataService service) {
		LogHelper.error(response.toString());
		Toast toast = Toast.makeText(getContext(), response.getErrorMsg(),
				Toast.LENGTH_SHORT);
		toast.show();


		onEnd();
	}

	@Override
	public void onRequestError(int errorCode, byte[] errorResponse,
							   Throwable throwable, DataService service) {
		if (errorCode == -2) {
			LogHelper.error("network exception");
			Toast toast = Toast.makeText(getContext(), "网络异常", Toast.LENGTH_SHORT);
			toast.show();
		}

		if (errorCode == -1) {
			LogHelper.error("data_parse_exception");
			Toast toast = Toast.makeText(getContext(), "数据解析错误", Toast.LENGTH_SHORT);
			toast.show();
		}

		onEnd();
	}

	public void loadOver(boolean over) {
		tableView.loadOver(over);
	}

	public void onEnd() {
		tableView.reloadData();
		tableView.endRefresh();
		tableView.endLoadMore();
	}
}
