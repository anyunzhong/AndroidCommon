package net.datafans.android.common.widget.table;

import android.view.LayoutInflater;
import android.view.View;

public abstract class TableViewCell<T> {

	protected View cell;

	public TableViewCell(int layout, LayoutInflater flater) {
		cell = flater.inflate(layout, null);
	}

	protected View getView() {
		return cell;
	}
	
	protected abstract void refresh(T t);

}
