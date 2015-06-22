package net.datafans.android.common.widget.table.refresh;

import android.view.View;

public abstract class ListViewAdapter {

	private ListViewListener listener;

	public abstract View getListView();

	public abstract void enableRefresh(boolean enable);

	public abstract void enableLoadMore(boolean enable);

	public abstract void enableAutoLoadMore(boolean enable);

	public abstract void endRefresh();

	public abstract void endLoadMore();

	public abstract void loadOver(boolean over);


	public abstract void hideDivider();


	public ListViewListener getListener() {
		return listener;
	}

	public void setListener(ListViewListener listener) {
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
