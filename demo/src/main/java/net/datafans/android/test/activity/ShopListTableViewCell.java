package net.datafans.android.test.activity;

import net.datafans.android.common.widget.imageview.CommonImageView;
import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.test.R;
import net.datafans.android.test.data.service.Shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ShopListTableViewCell extends TableViewCell<Shop> {

	@InjectView(R.id.name)
	TextView name;

	@InjectView(R.id.avatar)
	CommonImageView imageView;

	public ShopListTableViewCell() {
		super(R.layout.shop_list);
		arrow.setVisibility(View.VISIBLE);
	}


	@Override
	protected void refresh(Shop shop) {
		name.setText(shop.getNoteName());
		imageView.loadImage("http://coder-file.oss-cn-hangzhou.aliyuncs.com/avatar/1.jpeg");
	}

}
