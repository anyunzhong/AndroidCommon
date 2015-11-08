package net.datafans.android.common.widget.table.refresh.adapter;

import net.datafans.android.common.lib.refresh.pulldown.PullDownView;
import net.datafans.android.common.lib.refresh.pulldown.PullDownView.OnPullDownListener;
import net.datafans.android.common.widget.table.refresh.RefreshTableViewAdapter;
import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class PullDownRefreshTableViewAdapter extends RefreshTableViewAdapter implements
		OnPullDownListener {

	private PullDownView pullDownView;

	public PullDownRefreshTableViewAdapter(Context context, BaseAdapter adapter) {
		pullDownView = new PullDownView(context);
		pullDownView.setOnPullDownListener(this);
		//pullDownView.getListView().setAdapter(adapter);
		pullDownView.enableAutoFetchMore(true, 1);

	}

	@Override
	public View getRootView() {
		return pullDownView;
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
		refresh();
	}

	@Override
	public void onMore() {
		loadMore();
	}

	@Override
	public void endRefresh() {
		pullDownView.RefreshComplete();
	}

	@Override
	public void endLoadMore() {
		pullDownView.notifyDidMore();
	}

	@Override
	public void enableAutoLoadMore(boolean enable) {

	}

	@Override
	public void loadOver(boolean over) {

	}

}
