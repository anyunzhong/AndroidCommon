package net.datafans.android.test.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import net.datafans.android.common.widget.imageview.CommonImageView;
import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.test.R;
import net.datafans.android.test.data.service.Shop;

import butterknife.InjectView;

public class SectionTableViewCell extends TableViewCell<String> {

	@InjectView(R.id.title)
	TextView titleView;


	public SectionTableViewCell() {
		super(R.layout.section_table_item);
		arrow.setVisibility(View.GONE);
	}


	@Override
	protected void refresh(String title) {
		titleView.setText(title);
	}

}
