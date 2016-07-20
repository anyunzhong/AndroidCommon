package net.datafans.android.common.widget.table.refresh.adapter;

import cn.trinea.android.common.view.DropDownListView;
import cn.trinea.android.common.view.DropDownListView.OnDropDownListener;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import net.datafans.android.common.widget.table.refresh.RefreshTableViewAdapter;

public class DropDownRefreshTableViewAdapter extends RefreshTableViewAdapter {

	private DropDownListView listView;

	public DropDownRefreshTableViewAdapter(Context context, BaseAdapter adapter) {

		listView = new DropDownListView(context);
		//listView.setAdapter(adapter);
		listView.setDropDownStyle(true);
		listView.setOnBottomStyle(true);
		listView.setShowFooterProgressBar(true);
		listView.setShowFooterWhenNoMore(true);


		listView.setHeaderDefaultText("点击刷新");
		listView.setHeaderPullText("下拉刷新");
		listView.setHeaderReleaseText("释放刷新");

	}

	@Override
	public View getRootView() {

		return listView;
	}

	@Override
	public ListView getListView() {
		return listView;
	}

	@Override
	public void enableRefresh(boolean enable) {

		listView.setOnDropDownListener(new OnDropDownListener() {

			@Override
			public void onDropDown() {
				//listView.setOnBottomStyle(true);
				listView.setHasMore(true);
				refresh();
			}
		});
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
		listView.onDropDownComplete();
	}

	@Override
	public void endLoadMore() {
		listView.onBottomComplete();
	}

	@Override
	public void loadOver(boolean over) {
		listView.setHasMore(false);
		//listView.setOnBottomStyle(false);
	}

}
