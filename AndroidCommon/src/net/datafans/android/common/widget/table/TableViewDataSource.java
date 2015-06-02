package net.datafans.android.common.widget.table;

public interface TableViewDataSource<T> {
	int getRows();

	TableViewCell<T> getTableViewCell(int row);
	
	T getEntity(int row);
}
