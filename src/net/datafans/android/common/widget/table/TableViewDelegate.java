package net.datafans.android.common.widget.table;

public interface TableViewDelegate {
	void onClickRow(int row);

	void onRefresh();

	void onLoadMore();
}
