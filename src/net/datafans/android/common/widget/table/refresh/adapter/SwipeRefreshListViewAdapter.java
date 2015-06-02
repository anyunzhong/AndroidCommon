package net.datafans.android.common.widget.table.refresh.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import net.datafans.android.common.widget.table.refresh.ListViewAdapter;

public class SwipeRefreshListViewAdapter extends ListViewAdapter implements
		OnRefreshListener {

	private ListView listView;
	private SwipeRefreshLayout refreshLayout;

	public SwipeRefreshListViewAdapter(Context context, BaseAdapter adapter) {
		refreshLayout = new SwipeRefreshLayout(context);
		listView = new ListView(context);
		refreshLayout.addView(listView);
		listView.setAdapter(adapter);

		refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
				android.R.color.holo_red_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_green_light);

	}

	@Override
	public View getListView() {
		return refreshLayout;
	}

	@Override
	public void enableRefresh(boolean enable) {
		refreshLayout.setOnRefreshListener(this);
	}

	@Override
	public void enableLoadMore(boolean enable) {

	}

	@Override
	public void endRefresh() {
		refreshLayout.setRefreshing(false);
	}

	@Override
	public void endLoadMore() {

	}

	@Override
	public void onRefresh() {
		refresh();
	}

	@Override
	public void enableAutoLoadMore(boolean enable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadOver(boolean over) {
		// TODO Auto-generated method stub
		
	}

}
