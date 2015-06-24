package net.datafans.android.common.widget.table.refresh.adapter;

import net.datafans.android.common.R;
import net.datafans.android.common.widget.table.refresh.ListViewAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class SwipeRefreshListViewAdapter extends ListViewAdapter implements
		OnRefreshListener {

	private ListView listView;
	private SwipeRefreshLayout refreshLayout;

	@SuppressLint("InflateParams")
	public SwipeRefreshListViewAdapter(Context context, BaseAdapter adapter) {

		View view = LayoutInflater.from(context).inflate(
				R.layout.swipe_refresh, null);
		refreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.id_swipe_ly);

		//
		// refreshLayout = new SwipeRefreshLayout(context);
		// listView = new ListView(context);
		// refreshLayout.addView(listView);

		listView = (ListView) view.findViewById(R.id.id_listview);

		listView.setAdapter(adapter);

		refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
				android.R.color.holo_red_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_green_light);

	}

	@Override
	public View getRootView() {
		return refreshLayout;
	}


	@Override
	public ListView getListView() {
		return listView;
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
