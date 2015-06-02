package net.datafans.android.common.widget.controller;

import net.datafans.android.common.data.service.BaseResponse;
import net.datafans.android.common.widget.table.TableView;
import net.datafans.android.common.widget.table.TableViewDataSource;
import net.datafans.android.common.widget.table.TableViewDelegate;
import net.datafans.android.common.widget.table.TableViewFragment;
import net.datafans.android.common.widget.table.refresh.RefreshControlType;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

public abstract class TableViewController<T> extends Controller implements
		TableViewDataSource<T>, TableViewDelegate {

	private TableView<T> tableView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected Fragment getRootFragment() {

		if (tableView == null) {
			tableView = new TableView<T>(this, getRefreshControlType(),
					enableRefresh(), enableLoadMore(), enableAutoLoadMore());
			tableView.setDataSource(this);
			tableView.setDelegate(this);

		}
		return new TableViewFragment<T>(tableView);
	}

	@Override
	protected void onStatusOk(BaseResponse response, Class<?> type) {
		super.onStatusOk(response, type);
		onEnd();
	}

	@Override
	public void onStatusError(BaseResponse response) {
		super.onStatusError(response);
		onEnd();
	}

	@Override
	public void onRequestError(int errorCode, byte[] errorResponse,
			Throwable throwable) {
		super.onRequestError(errorCode, errorResponse, throwable);
		onEnd();
	}

	public void onEnd() {
		tableView.reloadData();
		tableView.endRefresh();
		tableView.endLoadMore();
	}

	protected RefreshControlType getRefreshControlType() {
		return RefreshControlType.PullToRefresh;
	}

	protected void loadOver(boolean over) {
		if (over) {
			Toast toast = Toast.makeText(this, "加载完毕", Toast.LENGTH_SHORT);
			toast.show();
		}
		tableView.loadOver(over);
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
}
