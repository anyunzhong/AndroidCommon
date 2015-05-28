package net.datafans.android.common.widget.table;

import net.datafans.android.common.data.service.BaseResponse;
import net.datafans.android.common.widget.base.Controller;
import android.app.Fragment;
import android.os.Bundle;

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
			tableView = new TableView<T>(this);
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
	
	public void onEnd(){
		tableView.reloadData();
		tableView.endRefresh();
		tableView.endLoadMore();
	}

}
