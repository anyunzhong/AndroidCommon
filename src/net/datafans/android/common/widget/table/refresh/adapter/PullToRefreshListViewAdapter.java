package net.datafans.android.common.widget.table.refresh.adapter;

import net.datafans.android.common.widget.table.refresh.ListViewAdapter;
import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class PullToRefreshListViewAdapter extends ListViewAdapter {

	private PullToRefreshListView listView;

	public PullToRefreshListViewAdapter(Context context, BaseAdapter adapter) {
		listView = new PullToRefreshListView(context);
		listView.setAdapter(adapter);
		listView.setMode(Mode.BOTH);
		listView.getLoadingLayoutProxy(false, true).setPullLabel("下拉刷新");  
		listView.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");  
		listView.getLoadingLayoutProxy(false, true).setReleaseLabel("上拉加载更多");  

	}

	@Override
	public View getListView() {
		return listView;
	}

	@Override
	public void enableRefresh(boolean enable) {

		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {

				if (listener == null) {
					return;
				}
				listener.onRefresh();

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				if (listener == null) {
					return;
				}
				
				listener.onLoadMore();

			}
		});
	}

	@Override
	public void enableLoadMore(boolean enable) {
	}

	@Override
	public void endRefresh() {
		listView.onRefreshComplete();
	}

	@Override
	public void endLoadMore() {
		listView.onRefreshComplete();
	}

}
