package net.datafans.android.common.widget.table;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TableViewFragment<T> extends Fragment {

	private TableView<T> tableView;

	public TableViewFragment(TableView<T> tableView) {
		this.tableView = tableView;
	}
	
	public TableViewFragment(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return tableView.getView();
	}
}
