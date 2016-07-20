package net.datafans.android.test.activity;

import android.content.Context;
import android.widget.TextView;

import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.test.R;

/**
 * Created by zhonganyun on 15/10/12.
 */
public class EditTextTableViewCell extends TableViewCell<String> {

    private TextView titleView;

    public EditTextTableViewCell() {
        super(R.layout.section_table_item);

        titleView = (TextView) cell.findViewById(R.id.title);
    }

    @Override
    protected void refresh(String s) {
        titleView.setText(s);
    }
}
