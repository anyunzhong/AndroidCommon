package net.datafans.android.common.widget.table.sectionindex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SectionIndexTableViewFragment<T> extends Fragment {

	private SectionIndexTableView<T> tableView;

	public SectionIndexTableViewFragment(SectionIndexTableView<T> tableView) {
		this.tableView = tableView;
	}

	public SectionIndexTableViewFragment(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return tableView.getView();
	}
}
