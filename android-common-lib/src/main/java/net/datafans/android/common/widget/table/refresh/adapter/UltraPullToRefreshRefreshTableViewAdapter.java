package net.datafans.android.common.widget.table.refresh.adapter;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import net.datafans.android.common.widget.table.refresh.RefreshTableViewAdapter;

public class UltraPullToRefreshRefreshTableViewAdapter extends RefreshTableViewAdapter {

	//private PtrFrameLayout frameLayout;
	private ListView listView;

	public UltraPullToRefreshRefreshTableViewAdapter(Context context,
													 BaseAdapter adapter) {

//		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		frameLayout = new PtrFrameLayout(context);
//		frameLayout.setLayoutParams(params);
//		listView = new ListView(context);
//		frameLayout.addView(listView);
//		//listView.setAdapter(adapter);
//
//		listView.setBackgroundColor(Color.BLUE);
//		listView.setLayoutParams(params);
//
//		// the following are default settings
//		frameLayout.setResistance(1.7f);
//		frameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
//		frameLayout.setDurationToClose(200);
//		frameLayout.setDurationToCloseHeader(1000);
//		// default is false
//		frameLayout.setPullToRefresh(true);
//		// default is true
//		frameLayout.setKeepHeaderWhenRefresh(true);
	}

	@Override
	public View getRootView() {
		//return frameLayout;
		return null;
	}



	@Override
	public ListView getListView() {
		return listView;
	}

	@Override
	public void enableRefresh(boolean enable) {

//		frameLayout.setPtrHandler(new PtrHandler() {
//			@Override
//			public void onRefreshBegin(PtrFrameLayout frame) {
//				refresh();
//			}
//
//			@Override
//			public boolean checkCanDoRefresh(PtrFrameLayout frame,
//					View content, View header) {
//				return PtrDefaultHandler.checkContentCanBePulledDown(frame,
//						content, header);
//			}
//		});
	}

	@Override
	public void enableLoadMore(boolean enable) {
	}

	@Override
	public void endRefresh() {
		//frameLayout.refreshComplete();
	}

	@Override
	public void endLoadMore() {
		//frameLayout.refreshComplete();
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
