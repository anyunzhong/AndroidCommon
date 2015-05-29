package net.datafans.android.common.widget.table.refresh.adapter;

import net.datafans.android.common.lib.refresh.pulldown.PullDownView;
import net.datafans.android.common.lib.refresh.pulldown.PullDownView.OnPullDownListener;
import net.datafans.android.common.widget.table.refresh.ListViewAdapter;
import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class PullDownListViewAdapter extends ListViewAdapter implements
		OnPullDownListener {

	private PullDownView pullDownView;

	public PullDownListViewAdapter(Context context, BaseAdapter adapter) {
		pullDownView = new PullDownView(context);
		pullDownView.setOnPullDownListener(this);
		pullDownView.getListView().setAdapter(adapter);
		pullDownView.enableAutoFetchMore(true, 1);

	}

	@Override
	public ListView getListView() {
		return pullDownView.getListView();
	}

	@Override
	public void enableRefresh(boolean enable) {
		if (enable)
			pullDownView.setShowHeader();
		else
			pullDownView.setHideHeader();
	}

	@Override
	public void enableLoadMore(boolean enable) {
		if (enable)
			pullDownView.setShowFooter();
		else
			pullDownView.setHideFooter();
	}

	@Override
	public void onRefresh() {
		if (listener == null) {
			return;
		}
		listener.onRefresh();
	}

	@Override
	public void onMore() {
		if (listener == null) {
			return;
		}
		listener.onLoadMore();
	}

	@Override
	public void endRefresh() {
		pullDownView.RefreshComplete();
	}

	@Override
	public void endLoadMore() {
		pullDownView.notifyDidMore();
	}

}
