package net.datafans.android.common.widget.imageview;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;

import com.loopj.android.image.SmartImageView;

public class SmartImageViewAdapter extends ImageViewAdapter {

	private SmartImageView imageView;

	public SmartImageViewAdapter(Context context) {
		imageView = new SmartImageView(context);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		imageView.setLayoutParams(params);
	}

	@Override
	public View getImageView() {
		return imageView;
	}

	@Override
	public void loadImage(String url) {
		imageView.setImageUrl(url);

	}

}
