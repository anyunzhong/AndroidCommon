package net.datafans.android.common.widget.table.refresh.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import net.datafans.android.common.R;
import net.datafans.android.common.widget.table.refresh.ListViewAdapter;

public class OriginListViewAdapter extends ListViewAdapter implements
		OnRefreshListener {

	private ListView listView;

	private LinearLayout layout;

	@SuppressLint("InflateParams")
	public OriginListViewAdapter(Context context, BaseAdapter adapter) {

		View view = LayoutInflater.from(context).inflate(
				R.layout.origin_listview, null);
		layout = (LinearLayout)view.findViewById(R.id.layout);

		listView = (ListView) layout.findViewById(R.id.listview);

		listView.setAdapter(adapter);

	}

	@Override
	public View getRootView() {
		return layout;
	}


	@Override
	public ListView getListView() {
		return listView;
	}

	@Override
	public void enableRefresh(boolean enable) {

	}

	@Override
	public void enableLoadMore(boolean enable) {

	}

	@Override
	public void endRefresh() {


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

	}

	@Override
	public void loadOver(boolean over) {

	}

}
