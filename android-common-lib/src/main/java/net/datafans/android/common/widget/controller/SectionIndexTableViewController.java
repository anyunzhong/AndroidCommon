package net.datafans.android.common.widget.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import net.datafans.android.common.widget.table.sectionindex.SectionIndexTableView;
import net.datafans.android.common.widget.table.sectionindex.SectionIndexTableViewDataSource;
import net.datafans.android.common.widget.table.sectionindex.SectionIndexTableViewDelegate;
import net.datafans.android.common.widget.table.sectionindex.SectionIndexTableViewFragment;
import net.datafans.android.common.widget.table.refresh.RefreshControlType;

/**
 * Created by zhonganyun on 15/8/17.
 */
public abstract class SectionIndexTableViewController<T> extends FragmentController implements SectionIndexTableViewDataSource<T>, SectionIndexTableViewDelegate {

    private SectionIndexTableView<T> tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment getRootFragment() {
        if (tableView == null) {
            tableView = new SectionIndexTableView<>(this, RefreshControlType.None, false, false, false, this, this);
        }
        return new SectionIndexTableViewFragment<>(tableView);
    }
}
