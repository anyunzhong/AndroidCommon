package net.datafans.android.common.widget.table;

public interface TableViewDataSource<T> {

	int getRows(int section);

	TableViewCell<T> getTableViewCell(int section, int row);
	
	T getEntity(int section, int row);

	int getItemViewType(int section, int row);

	int getItemViewTypeCount();
}
