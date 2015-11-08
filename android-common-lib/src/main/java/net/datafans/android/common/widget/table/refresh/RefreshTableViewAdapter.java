package net.datafans.android.common.widget.table.refresh;

import android.view.View;
import android.widget.ListView;

public abstract class RefreshTableViewAdapter {

	private RefreshTableViewListener listener;

	public abstract View getRootView();

	public abstract ListView getListView();

	public abstract void enableRefresh(boolean enable);

	public abstract void enableLoadMore(boolean enable);

	public abstract void enableAutoLoadMore(boolean enable);

	public abstract void endRefresh();

	public abstract void endLoadMore();

	public abstract void loadOver(boolean over);

	public RefreshTableViewListener getListener() {
		return listener;
	}

	public void setListener(RefreshTableViewListener listener) {
		this.listener = listener;
	}

	protected void refresh() {
		if (listener == null) {
			return;
		}

		listener.onRefresh();
	}

	protected void loadMore() {
		if (listener == null) {
			return;
		}
		listener.onLoadMore();
	}

}
