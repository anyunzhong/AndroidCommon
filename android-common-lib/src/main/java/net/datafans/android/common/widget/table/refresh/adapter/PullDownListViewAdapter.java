package net.datafans.android.common.widget.table.refresh.adapter;

import net.datafans.android.common.lib.refresh.pulldown.PullDownView;
import net.datafans.android.common.lib.refresh.pulldown.PullDownView.OnPullDownListener;
import net.datafans.android.common.widget.table.refresh.ListViewAdapter;
import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;

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
	public View getListView() {
		return pullDownView;
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

	@Override
	public void hideDivider() {

	}

}
