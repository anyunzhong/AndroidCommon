package net.datafans.android.common.widget.table.refresh.adapter;

import cn.trinea.android.common.view.DropDownListView;
import cn.trinea.android.common.view.DropDownListView.OnDropDownListener;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import net.datafans.android.common.widget.table.refresh.ListViewAdapter;

public class DropDownListViewAdapter extends ListViewAdapter {

	private DropDownListView listView;

	public DropDownListViewAdapter(Context context, BaseAdapter adapter) {

		listView = new DropDownListView(context);
		listView.setAdapter(adapter);
		listView.setDropDownStyle(true);
		listView.setOnBottomStyle(true);
		listView.setShowFooterProgressBar(true);

	}

	@Override
	public View getListView() {

		return listView;
	}

	@Override
	public void enableRefresh(boolean enable) {

		listView.setOnDropDownListener(new OnDropDownListener() {

			@Override
			public void onDropDown() {
				refresh();
			}
		});
	}

	@Override
	public void enableLoadMore(boolean enable) {

		listView.setOnBottomListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
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
		listView.onDropDownComplete();
	}

	@Override
	public void endLoadMore() {
		listView.onBottomComplete();
	}

	@Override
	public void loadOver() {
		listView.setHasMore(false);
	}

}
