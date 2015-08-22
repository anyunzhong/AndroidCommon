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

public class ShopListTableViewRedCell extends TableViewCell<Shop> {

	@InjectView(R.id.name)
	TextView name;

	@InjectView(R.id.avatar)
	CommonImageView imageView;

	public ShopListTableViewRedCell(int layout, Context context) {
		super(layout, context);
		//arrow.setVisibility(View.VISIBLE);
	}


	@Override
	protected void refresh(Shop shop) {
		name.setText(shop.getNoteName());
		imageView.loadImage("http://coder-file.oss-cn-hangzhou.aliyuncs.com/avatar/1.jpeg");
	}

}
