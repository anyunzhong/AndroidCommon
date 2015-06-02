package net.datafans.android.common.widget.table.refresh.adapter;

import net.datafans.android.common.widget.table.refresh.ListViewAdapter;
import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.AnimationStyle;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class PullToRefreshListViewAdapter extends ListViewAdapter {

	private PullToRefreshListView listView;

	public PullToRefreshListViewAdapter(Context context, BaseAdapter adapter) {
		listView = new PullToRefreshListView(context,Mode.BOTH,AnimationStyle.ROTATE);
		listView.setAdapter(adapter);
		
		ILoadingLayout layout = listView.getLoadingLayoutProxy(false,true);
		layout.setPullLabel("上拉更多");
		layout.setRefreshingLabel("加载中...");
		layout.setReleaseLabel("松开加载");

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
				refresh();

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				loadMore();

			}
		});
	}

	@Override
	public void enableLoadMore(boolean enable) {
	}

	@Override
	public void endRefresh() {
		listView.setMode(Mode.BOTH);
		listView.onRefreshComplete();
	}

	@Override
	public void endLoadMore() {
		listView.onRefreshComplete();
	}

	@Override
	public void enableAutoLoadMore(boolean enable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadOver() {
		listView.setMode(Mode.PULL_FROM_START);
	}

}
