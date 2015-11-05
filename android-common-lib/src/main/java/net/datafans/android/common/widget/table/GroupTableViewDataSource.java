package net.datafans.android.common.widget.table;

public interface GroupTableViewDataSource<T> extends TableViewDataSource<T> {

	int getSections();

	int getSectionHeaderHeight(int section);

	int getSectionFooterHeight(int section);

	String getSectionHeaderTitle(int section);

	String getSectionFooterTitle(int section);
}
