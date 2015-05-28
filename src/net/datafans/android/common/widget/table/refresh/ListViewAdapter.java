package net.datafans.android.common.widget.table.refresh;

import android.view.View;

public abstract class ListViewAdapter {
	
	protected ListViewListener listener;
	
	public abstract View getListView();

	public abstract void enableRefresh(boolean enable);

	public abstract void enableLoadMore(boolean enable);
	
	public abstract void endRefresh();
	public abstract void endLoadMore();

	public ListViewListener getListener() {
		return listener;
	}

	public void setListener(ListViewListener listener) {
		this.listener = listener;
	}
	
	
}
