package net.datafans.android.common.widget.table;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class TableViewCell<T> {

    protected ViewGroup cell;

    public TableViewCell(int layout, LayoutInflater flater) {
        cell = (ViewGroup)flater.inflate(layout, null);

        ButterKnife.inject(this, cell);
    }

    protected ViewGroup getView() {
        return cell;
    }

    protected abstract void refresh(T t);

}
