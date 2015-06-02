package net.datafans.android.common.widget.table.refresh.adapter;

import net.datafans.android.common.R;
import net.datafans.android.common.widget.table.refresh.ListViewAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout.BGARefreshLayoutDelegate;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import cn.trinea.android.common.view.DropDownListView;

public class BGANormalListViewAdapter extends ListViewAdapter implements
		BGARefreshLayoutDelegate {

	private BGARefreshLayout refreshLayout;
	private BGARefreshViewHolder refreshViewHolder;

	private DropDownListView listView;

	@SuppressLint("InflateParams")
	public BGANormalListViewAdapter(Context context, BaseAdapter adapter) {
		View view = LayoutInflater.from(context).inflate(R.layout.bga, null);
		refreshLayout = (BGARefreshLayout) view
				.findViewById(R.id.rl_modulename_refresh);
		refreshLayout.setDelegate(this);

		refreshViewHolder = new BGANormalRefreshViewHolder(context, false);
		refreshLayout.setRefreshViewHolder(refreshViewHolder);

		listView = (DropDownListView) view
				.findViewById(R.id.rl_modulename_refresh_listview);
		listView.setAdapter(adapter);

		listView.setDropDownStyle(false);
		listView.setOnBottomStyle(true);
		listView.setShowFooterProgressBar(true);
		listView.setShowFooterWhenNoMore(true);

	}

	@Override
	public View getListView() {
		return refreshLayout;
	}

	@Override
	public void enableRefresh(boolean enable) {

	}

	@Override
	public void enableLoadMore(boolean enable) {

		listView.setOnBottomListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listView.onBottomBegin();
				loadMore();
			}
		});

	}

	@Override
	public void enableAutoLoadMore(boolean enable) {
		listView.setAutoLoadOnBottom(enable);
	}

	@Override
	public void endRefresh() {
		refreshLayout.endRefreshing();
	}

	@Override
	public void endLoadMore() {
		// refreshLayout.endRefreshing();
		listView.onBottomComplete();
	}

	@Override
	public void loadOver() {
		listView.setHasMore(false);
	}

	@Override
	public void onBGARefreshLayoutBeginRefreshing() {
		listView.setHasMore(true);
		refresh();
	}

	@Override
	public void onBGARefreshLayoutBeginLoadingMore() {

		// loadMore();
	}

}
