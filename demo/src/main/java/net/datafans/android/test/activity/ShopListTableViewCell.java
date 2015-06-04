package net.datafans.android.test.activity;

import net.datafans.android.common.widget.imageview.CommonImageView;
import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.test.R;
import net.datafans.android.test.data.service.Shop;
import android.view.LayoutInflater;
import android.widget.TextView;

public class ShopListTableViewCell extends TableViewCell<Shop> {

	private TextView name;
	private CommonImageView imageView;

	public ShopListTableViewCell(int layout, LayoutInflater flater) {
		super(layout, flater);

		name = (TextView) cell.findViewById(R.id.name);
		imageView = (CommonImageView) cell.findViewById(R.id.avatar);
	}


	@Override
	protected void refresh(Shop shop) {
		name.setText(shop.getNoteName());
		imageView.loadImage("http://coder-file.oss-cn-hangzhou.aliyuncs.com/avatar/1.jpeg");
	}

}
