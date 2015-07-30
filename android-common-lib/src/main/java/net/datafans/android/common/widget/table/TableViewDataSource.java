package net.datafans.android.common.widget.table;

public interface TableViewDataSource<T> {

	int getSections();

	int getRows(int section);

	int getSectionHeaderHeight(int section);

	int getSectionFooterHeight(int section);


	String getSectionHeaderTitle(int section);

	String getSectionFooterTitle(int section);

	TableViewCell<T> getTableViewCell(int section, int row);
	
	T getEntity(int section, int row);
}
